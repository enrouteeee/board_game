<template>
  <v-container>
    <v-row>
      <v-col cols="12">
        <h1 v-if="gameState==='INIT' && initCount===4">
            준비 완료
        </h1>
        <h1 v-else-if="gameState==='INIT'">
          카드 {{4-initCount}} 장을 선택해주세요
        </h1>
        <h1 v-if="gameState==='PLAYING_SELECT'">
          {{turn}} 플레이어 보드판에서 카드를 선택하세요
        </h1>
        <h1 v-if="gameState==='PLAYING_PREDICT'">
          {{turn}} 플레이어 다른 플레이어의 카드를 예측하세요
        </h1>
        <h1 v-if="gameState==='FINISH'">
          게임이 종료되었습니다.
        </h1>
      </v-col>
      <v-col cols="5">
        <v-container>
          <v-row>
            <v-col v-for="(player, idx) in playerCards" :key="idx" cols="12">
              <v-card>
                <v-card-title>
                  플레이어 {{idx + 1}}
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
      
      <v-col cols="1">
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
      turn: 0,            //  현재 누구 차례인지 index of order
      gameState: "INIT",  //  INIT, PLAYING_SELECT, PLAYING_PREDICT, FINISH
      plyerState: [],     //  각 플레이어의 상태
      initCount: 0,       //  게임 시작시 몇 장 가져왔는지

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

        console.log(content);

        // 뽑은 사람 찾기
        var i;
        for(i=0; i<this.order.length; i++){
          if(this.order[i].nickname === content.sender){
            break;
          }
        }

        // 보드판에서 뽑은사람한테 카드 주기
        var selectedCard = this.boardCards[content.content];
        this.boardCards.splice(content.content, 1);
        this.playerCards[i].push(selectedCard);

        // 게임 상태에 따른 작업
        if(this.gameState === "INIT"){
          console.log("ddddd")
          console.log(this.order[i].id);
          console.log(this.nickname);
          console.log(this.userId);
          if(this.order[i].id === this.userId) {
            this.initCount++;
          }
          var flag = true;
          for(var player in this.playerCards) {
            if(player.length !== 4) {
              flag = false;
              break;
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
      if((this.gameState === "INIT" && this.initCount < 4) || (this.gameState === "PLAYING_SELECT" && this.turn === this.userId)){
        console.log("요기");
        this.stomp.send(
          "/pub/game",
          JSON.stringify({
            sender: this.nickname,
            type:"SELECT_CARD",
            gameId:this.gameId,
            content:idx
          })
        );
      }
    },

  }
}
</script>

<style>

</style>