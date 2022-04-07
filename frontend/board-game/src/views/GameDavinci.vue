<template>
  <v-container>
    <v-row>
      <v-col cols="7"></v-col>
      <v-col cols="2">
        <v-btn v-if="gameState === 'FINISH'" @click="returnToRoom">방으로 돌아가기</v-btn>
      </v-col>
      <v-col cols="1"></v-col>
      <v-col cols="2">
        <v-btn @click="exitGame">나가기</v-btn>
      </v-col>
      <v-col cols="6">
        <h1 v-if="gameState==='INIT' && initCount===4">
            준비 완료
        </h1>
        <h1 v-else-if="gameState==='INIT'">
          카드 {{4-initCount}} 장을 선택해주세요
        </h1>
        <h1 v-if="gameState==='PLAYING_SELECT'">
          {{order[turn].nickname}} 님 보드판에서 카드를 선택하세요
        </h1>
        <h1 v-if="gameState==='PLAYING_PREDICT'">
          {{order[turn].nickname}} 님 다른 플레이어의 카드를 예측하세요
        </h1>
        <h1 v-if="gameState==='FINISH'">
          게임이 종료되었습니다. 승자는 {{victoryNickname}} 님 입니다.
        </h1>
      </v-col>
      <v-col cols="6">
        <h2>{{ predictState }}</h2>
      </v-col>
      <v-col cols="6">
        <v-container>
          <v-row>
            <v-col v-for="(player, idx) in playerCards" :key="idx" cols="12">
              <v-card>
                <v-card-title>
                  {{order[idx].nickname}}
                  <span v-if="playerState[idx]==='DIE'"> 죽음</span>
                </v-card-title>
                <v-container>
                  <v-row>
                    <v-col sm="2" v-for="(card, idx2) in player" :key="idx2" @click="predictCard(idx, idx2)">
                      <v-card dark v-if="card.color==='black'" v-bind:color="card.color">
                        <v-card-text v-if="order[idx].id==userId">
                          {{ card.number }}
                          <div v-if="card.flipped">들킴</div>
                        </v-card-text>
                        <v-card-text v-else-if="card.flipped">
                          {{ card.number }}
                        </v-card-text>
                        <v-card-text v-else>
                          ?
                        </v-card-text>
                      </v-card>

                      <v-card v-else v-bind:color="card.color">
                        <v-card-text v-if="order[idx].id==userId">
                          {{ card.number }}
                          <div v-if="card.flipped">들킴</div>
                        </v-card-text>
                        <v-card-text v-else-if="card.flipped">
                          {{ card.number }}
                        </v-card-text>
                        <v-card-text v-else>
                          ?
                        </v-card-text>
                      </v-card>

                      <v-card-subtitle v-if="getLastCard(idx) === idx2">
                        ▴
                      </v-card-subtitle>

                    </v-col>
                  </v-row>
                </v-container>
              </v-card>

            </v-col>
          </v-row>
        </v-container>
      </v-col>

      <v-col cols="6">
        <v-card>
          <v-card-title>
            보드판
          </v-card-title>
          <v-container>
            <v-row>
              <v-col sm="2" v-for="(card, idx) in boardCards" :key="idx" @click="selectBoardCard(idx)">
                <v-card dark v-if="card.color==='black'" v-bind:color="card.color">
                  <v-card-text v-if="card.flipped">
                    {{ card.number }}
                  </v-card-text>
                  <v-card-text v-else>
                    ?
                  </v-card-text>
                </v-card>
                <v-card v-else v-bind:color="card.color">
                  <v-card-text v-if="card.flipped">
                    {{ card.number }}
                  </v-card-text>
                  <v-card-text v-else>
                    ?
                  </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>
        </v-card>
      </v-col>
      
      <v-dialog v-model="selectPositionModal" max-width="400" persistent>
        <v-card>
          <v-card-title>
            {{ selectedCardNumber }} 카드를 뽑았습니다.
          </v-card-title>
          <v-card-subtitle>
            몇 번째 위치에 놓을지 선택하세요.
          </v-card-subtitle>
          <v-card-text>
            <v-select
              :items=selectPositionItem
              label="위치선택"
              required
              v-model="selectedPosition"
            ></v-select>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click=selectPosition>선택하기</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="predictModal" max-width="400">
        <v-card>
          <v-card-text>
            <v-card-title>
              예측할 숫자를 고르세요
            </v-card-title>
            <v-select
              :items=predictItem
              label="숫자선택"
              required
              v-model="predictIdx"
            ></v-select>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click=predictNumber>예측하기</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="oneMoreModal" max-width="400" persistent>
        <v-card>
          <v-card-text>
            <v-card-title>
              예측을 계속할지 차례를 넘길지 선택하세요
            </v-card-title>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click=OneMorePredict>예측한다</v-btn>
            <v-btn color="green darken-1" text @click=passTurnClick>넘긴다</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

    </v-row>
  </v-container>
</template>

<script>
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'

export default {
  data() {
    return {
      userId: this.$store.state.userId,
      nickname: this.$store.state.nickname,
      gameId: this.$route.params.gameId,
      playerCards: [],    //  각 플레이어가 가지고 있는 카드 리스트(2차원 배열)
      
      boardCards: [],     //  보드판의 카드 리스트
      order: [],          //  플레이어 순서
      turn: 0,            //  현재 누구 차례인지 index of order : order[turn]
      gameState: "INIT",  //  INIT, PLAYING_SELECT, PLAYING_PREDICT, FINISH
      playerState: [],     //  각 플레이어의 상태 ALIVE, DIE
      initCount: 0,       //  게임 시작시 몇 장 가져왔는지
      selectPlayer: 0,  //  보드판에서 카드를 뽑은 플레이어 index : playerCards[selectPlayer]
      selectedCard: null, //  보드판에서 뽑은 카드
      selectedCardNumber: 0,  // 보드판에서 뽑은 카드 숫자 selectPositionModal 에서 사용

      selectPositionModal: false, // 여러 자리에 들어갈 경우 위치 선택 모달
      selectPositionItem: [],     // 여러 자리에 들어갈 경우 선택지
      selectedPosition: 0,        // 모달 창에서 선택한 위치

      predictModal: false,  // 다른 카드를 예측하는 선택 모달
      predictItem: [0,1,2,3,4,5,6,7,8,9,10,11,"-"],
      predictIdx: 0,        // 예측한 숫자
      predictPlayerIdx: 0,  // 예측한 플레이어  index
      predictCardIdx: 0,    // 예측한 플레이어의 카드 index

      predictState: "",     // 누가 누구를 에측 했는지 broadcast

      playerCardsOrder: [], //  각 플레이어가 뽑은 카드의 index를 순서대로 저장한 리스트(2차원 배열) : 카드 예측에 실패시 오픈할 카드를 구하기 위해
      
      oneMoreModal: false,  // 카드 예측 성공시 한번 더 에측할지 차례를 넘길지 선택

      victoryNickname: ""   // 이긴사람 닉네임
    }
  },
  created() {
    this.getGameInfo();

    this.connect();
  },
  methods: {
    getGameInfo() {
      try { 
        this.$axios
          .get("/api/game/davinci/"+this.gameId)
          .then((res) => {
          if (res.status === 200) {
            console.log(res.data);
            
            this.boardCards = res.data.board;
            var i;
            for(i =0; i<this.boardCards.length; i++){
              switch(this.boardCards[i].color){
                case "WHITE":
                  this.boardCards[i].color="white";
                  break;
                case "BLACK":
                  this.boardCards[i].color="black";
              }
              switch(this.boardCards[i].number){
                case "ZERO":
                  this.boardCards[i].number="0";
                  break;
                case "ONE":
                  this.boardCards[i].number="1";
                  break;
                case "TWO":
                  this.boardCards[i].number="2";
                  break;
                case "THREE":
                  this.boardCards[i].number="3";
                  break;
                case "FOUR":
                  this.boardCards[i].number="4";
                  break;
                case "FIVE":
                  this.boardCards[i].number="5";
                  break;
                case "SIX":
                  this.boardCards[i].number="6";
                  break;
                case "SEVEN":
                  this.boardCards[i].number="7";
                  break;
                case "EIGHT":
                  this.boardCards[i].number="8";
                  break;
                case "NINE":
                  this.boardCards[i].number="9";
                  break;
                case "TEN":
                  this.boardCards[i].number="10";
                  break;
                case "ELEVEN":
                  this.boardCards[i].number="11";
                  break;
                case "JOKER":
                  this.boardCards[i].number="-";
                  break;
              }
            }
            this.order = res.data.order;
            this.playerCards = new Array(res.data.order.length);
            this.playerCardsOrder =  new Array(res.data.order.length);
            this.playerState =  new Array(res.data.order.length);
            for(i = 0; i<this.playerCards.length; i++){
              this.playerCards[i] = [];
              this.playerCardsOrder[i] = [];
              this.playerState[i] = "ALIVE";
            }
          }
          });
      } catch (error) {
        console.log(error);
      }
    },
    connect() {
      const serverURL = "http://localhost:8080/ws-stomp"
      let socket = new SockJS(serverURL);
      this.stomp = Stomp.over(socket);
    
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stomp.connect({}, this.subGame);
    },
    subGame() {
      this.stomp.subscribe("/sub/game/"+this.gameId, this.subCommand);
    },
    subCommand(command) {
      try {
        var content = JSON.parse(command.body)
      } catch (error) {
        console.log(error);
      }
    
      if(content.type === "SELECT_CARD") {
        console.log("SELECT_CARD");

        // 뽑은 사람 찾기
        var i;
        for(i=0; i<this.order.length; i++){
          if(this.order[i].nickname === content.sender){
            break;
          }
        }
        this.selectPlayer = i;

        // 보드판에서 카드 가져가기
        this.selectedCard = this.boardCards[content.content.idx];
        this.boardCards.splice(content.content.idx, 1);

        if(this.order[i].nickname != this.nickname){
          return;
        }

        // 카드가 놓여질 수 있는 자리 뽑기
        var ablePosition = [];
        var j;
        if(this.selectedCard.number === "-"){
          for(j = 0; j<=this.playerCards[i].length; j++){
            ablePosition.push(j);
          }
        } else {
          for(j=0; j<=this.playerCards[i].length; j++){
            var check = true;
            var k;

            //왼쪽 으로
            for(k=j-1; k>=0; k--){
              if(this.playerCards[i][k].number === "-")
                continue;

              if(Number(this.playerCards[i][k].number) > Number(this.selectedCard.number)){
                check = false;
                break;
              } else if(Number(this.playerCards[i][k].number) === Number(this.selectedCard.number)){
                if(this.playerCards[i][k].color === "white"){
                  check = false;
                  break;
                }
              }
            }

            //오른쪽 으로
            for(k=j; k<this.playerCards[i].length; k++){
              if(this.playerCards[i][k].number === "-")
                continue;
              
              if(Number(this.playerCards[i][k].number) < Number(this.selectedCard.number)){
                check = false;
                break;
              } else if(Number(this.playerCards[i][k].number) === Number(this.selectedCard.number)){
                if(this.playerCards[i][k].color === "black"){
                  check = false;
                  break;
                }
              }
            }

            if(check) {
              ablePosition.push(j);
            }
          }
        }

        if(ablePosition.length == 1){
          this.selectedPosition = ablePosition[0];
          this.cardSeletedEvent();
        } else {
          this.selectedCardNumber = this.selectedCard.number
          this.selectPositionItem = ablePosition;
          this.selectPositionModal = true;
        }
      } else if (content.type === "SELECT_CARD_POSITION") {
        console.log("SELECT_CARD_POSITION");
        
        for(i=0; i<this.order.length; i++){
          if(this.order[i].nickname === content.sender){
            break;
          }
        }
        this.playerCards[i].splice(content.content.position, 0, content.content.card);
        this.playerCardsOrder[i].push(content.content.card);

        // 게임 상태에 따른 작업 후속 작업
        if(this.gameState === "INIT"){
          if(this.order[i].id === this.userId) {
            this.initCount++;
          }
          var flag = true;
          for(i=0; i<this.playerCards.length; i++){
            if(this.playerCards[i].length != 4) {
              flag = false;
            }
          }
          if(flag) {
            this.gameState = "PLAYING_SELECT";
          }
        } else if(this.gameState === "PLAYING_SELECT") {
          this.gameState = "PLAYING_PREDICT";
        }
      } else if (content.type === "PREDICT_CARD") {
        console.log("PREDICT_CARD");

        this.predictState = content.sender+"님이 "+this.order[content.content.playerIdx].nickname+"님의 "
          +(content.content.cardIdx+1)+"번째 카드를 "+content.content.number+"라고 예측했습니다.";

        if(this.playerCards[content.content.playerIdx][content.content.cardIdx].number == content.content.number){
          console.log("예측 성공!");
          this.predictState += "\n예측 성공!";
          // 예측한 카드 뒤집기
          if(this.cardFlip(content.content.playerIdx, content.content.cardIdx)){
            return;
          }

          if(this.order[this.turn].id === this.userId){
            // 차례를 넘길지, 예측을 계속할지
            this.oneMoreModal = true;
          }
        } else {
          console.log("예측 실패!");
          this.predictState += "\n예측 실패!";
          // 자신이 마지막으로 가져온 카드를 뒤집기

          for(i=0; i<this.order.length; i++){
            if(this.order[i].nickname === content.sender){
              break;
            }
          }
          
          // 예측실패 시 뒤집을 카드 찾기
          var c = this.getLastCard(i);

          if(this.cardFlip(i, c)){
            return;
          }

          // 턴 넘기기
          this.passTurn();
        }
      } else if (content.type === "PASS_TURN") {
        console.log("PASS_TURN");

        for(i=0; i<this.order.length; i++){
          if(this.order[i].nickname === content.sender){
            break;
          }
        }
        
        //턴 넘기기
        this.passTurn();
      } else if (content.type === "EXIT") {
        console.log("EXIT");

        // 나간 플레이어 카드 모두 뒤집기
        for(i=0; i<this.order.length; i++) {
          if(this.order[i].nickname === content.sender){
            for(j=0; j<this.playerCards[i].length; j++){
              this.cardFlip(i, j);
            }
            break;
          }
        }

        // 나가 플레이어 상태 변경
        this.playerDie(i);
      }

    },
    selectBoardCard(idx) {
      console.log("카드 뽑기", idx);
      // gameState === "INIT" 이면서 initCount 가 4 이하이거나, 내 차례에 선택할 시간 이거나
      if((this.gameState === "INIT" && this.initCount < 4) || (this.gameState === "PLAYING_SELECT" && this.order[this.turn].id === this.userId)){
        this.stomp.send(
          "/pub/game",
          JSON.stringify({
            sender: this.nickname,
            type:"SELECT_CARD",
            gameId:this.gameId,
            content:{
              idx: idx
            }
          })
        );
      }
    },
    selectPosition() {
      this.selectPositionModal = false;
      this.cardSeletedEvent();
    },
    cardSeletedEvent() {
      // 선택한 카드와 위치 정보를 서버로 보냄
      this.stomp.send(
        "/pub/game",
        JSON.stringify({
          sender: this.nickname,
          type:"SELECT_CARD_POSITION",
          gameId:this.gameId,
          content: {
            position: this.selectedPosition,
            card: this.selectedCard
          }
        })
      );
    },
    predictCard(idx, idx2) {
      console.log("카드 예측하기", idx, idx2);
      // gameState === "PLAYING_PREDICT", 내 차례이어야 함, 내 카드가 아니어야 함, 선택한 카드가 '?'카드 아니어야함
      if(this.gameState === "PLAYING_PREDICT"
        && this.order[this.turn].id === this.userId && this.order[idx].id != this.userId
        && this.playerCards[idx][idx2].flipped == false) {

        this.predictPlayerIdx = idx;
        this.predictCardIdx = idx2;
        this.predictModal = true;
      } 
    },
    predictNumber() {
      this.predictModal = false;

      this.stomp.send(
        "/pub/game",
        JSON.stringify({
          sender: this.nickname,
          type:"PREDICT_CARD",
          gameId:this.gameId,
          content: {
            playerIdx: this.predictPlayerIdx,
            cardIdx: this.predictCardIdx,
            number: this.predictIdx
          }
        })
      );
    },
    passTurn() {
      // 살아있는 사람 찾아서 턴 넘기기
      for(var c=1; c<this.order.length; c++){
        if(this.playerState[(this.turn+c)%this.order.length] === "ALIVE"){
          this.turn = (this.turn+c)%this.order.length;
          break;
        }
      }
      // 보드판에 카드가 있으면 PLAYING_SELECT, 없으면 PLAYING_PREDICT
      if(this.boardCards.length === 0){
        this.gameState = "PLAYING_PREDICT";
      } else {
        this.gameState = "PLAYING_SELECT";
      }
    },
    OneMorePredict() {
      this.oneMoreModal = false;
    },
    passTurnClick() {
      this.oneMoreModal = false;
      this.stomp.send(
        "/pub/game",
        JSON.stringify({
          sender: this.nickname,
          type:"PASS_TURN",
          gameId:this.gameId,
        })
      );
    },
    getLastCard(idx) {
      var card;
      for(var c=this.playerCardsOrder[idx].length-1; c>=0; c--){
        if(this.playerCardsOrder[idx][c].flipped === false){
          card = this.playerCardsOrder[idx][c];
          break;
        }
      }
      for(c=0; c<this.playerCards[idx].length; c++){
        if(card === this.playerCards[idx][c]){
          break;
        }
      }
      return c;
    },
    cardFlip(playerIdx, cardIdx) {
      // 게임 끝났으면 return true, 게임 안끝났으면 return fasle

      this.playerCards[playerIdx][cardIdx].flipped = true;

      var flag = true; // 뒤집한 플레이어 상태 확인 true : die, false : alive
      for(var i=0; i<this.playerCards[playerIdx].length; i++){
        if(this.playerCards[playerIdx][i].flipped === false){
          flag = false;
          break;
        }
      }
      if(flag) {
        return this.playerDie(playerIdx);
      }

      return false;
    },
    playerDie(idx) {
      this.playerState[idx] = "DIE";

      var cnt = 0;
      for(var i=0; i<this.playerState.length; i++) {
        if(this.playerState[i] === "DIE"){
          cnt++;
        } else if(this.playerState[i] == "ALIVE") {
          this.victoryNickname = this.order[i].nickname;
        }
      }

      if(cnt === this.playerState.length-1){
        // 게임 끝
        console.log("게임끝");

        //게임 끝 로직
        this.finishGame();
        return true;
      }
      return false;
    },
    finishGame() {
      this.gameState = "FINISH";
    },
    returnToRoom() {
      this.stomp.disconnect();
      this.$router.go(-1);
    },
    exitGame() {
      this.stomp.send(
        "/pub/game",
        JSON.stringify({
          sender: this.nickname,
          type:"EXIT",
          gameId:this.gameId,
        })
      );
      if(this.stomp){
        this.stomp.disconnect();
      }
      this.$router.push("/room-list");
    },
  }
}
</script>

<style>

</style>