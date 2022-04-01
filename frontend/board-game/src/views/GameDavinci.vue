<template>
  <v-container>
    <v-btn @click="exitGame">
      나가기
    </v-btn>
    <v-row>
      <v-col cols="12">
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
          게임이 종료되었습니다.
        </h1>
      </v-col>
      <v-col cols="6">
        <v-container>
          <v-row>
            <v-col v-for="(player, idx) in playerCards" :key="idx" cols="12">
              <v-card>
                <v-card-title>
                  {{order[idx].nickname}}
                </v-card-title>
                <v-container>
                  <v-row>
                    <v-col sm="2" v-for="(card, idx2) in player" :key="idx2">
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
      
      <v-dialog v-model="selectPositionModal" max-width="400">
        <v-card>
          <v-card-text>
            <v-card-title>
              카드가 들어갈 위치를 선택해주세요
            </v-card-title>
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
      plyerState: [],     //  각 플레이어의 상태
      initCount: 0,       //  게임 시작시 몇 장 가져왔는지
      selectPlayer: 0,  //  보드판에서 카드를 뽑은 플레이어 index : playerCards[selectPlayer]
      selectedCard: null, //  보드판에서 뽑은 카드

      selectPositionModal: false, // 여러 자리에 들어갈 경우 위치 선택 모달
      selectPositionItem: [],     // 여러 자리에 들어갈 경우 선택지
      selectedPosition: 0,        // 모달 창에서 선택한 위치
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
              this.boardCards[i].flipped = true
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
            for(i = 0; i<this.playerCards.length; i++){
              this.playerCards[i] = [];
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
    exitGame() {
      this.$router.push("/room-list");
    }

  }
}
</script>

<style>

</style>