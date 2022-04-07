<template>
  <v-container>
    <v-row>
      <v-col cols="3">
        <h1>{{ roomName }}</h1>
      </v-col>
      <v-col cols="3">
        <h1>다빈치코드</h1>
      </v-col>
      <v-col cols="3">
        <v-btn @click="startGame">
          게임시작
        </v-btn>
      </v-col>
      <v-col cols="3">
        <v-btn @click="exitRoom">
          나가기
        </v-btn>
      </v-col>
      
        <v-col cols="12">
          <v-card>
            <v-container
              id="scroll-target"
              style="max-height: 400px"
              class="overflow-y-auto">
              <v-row align-content="start" v-scroll:#scroll-target="onScroll" style="height: 400px">
                <v-col cols="12" v-for="(item, idx) in chatList" :key="idx">
                  {{ item }}
                </v-col>
              </v-row>
            </v-container>
          </v-card>
        </v-col>

      <v-col cols="12">
        <div class="inner-wrap">
          <v-text-field v-model="msg" label="chat" placeholder="보낼 메세지를 입력하세요." solo @keypress.enter="submitChat">
          </v-text-field>
        </div>
      </v-col>
      <v-col v-for="(item, idx) in userList" :key="idx" cols="2">
        <v-card>
          <v-card-title>{{ item.nickname }}</v-card-title>
          <v-sub-title v-if="ownerId === item.id">방장</v-sub-title>
          <v-card-text>
            <v-icon color="black" size="40">person</v-icon>
          </v-card-text>
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
      roomId: this.$route.params.roomId,
      roomName: "",
      ownerId: "",
      userList: [],
      chatList: [],
      msg: "",
    }
  },
  created() {
    this.getRoomInfo();

    this.connect();
  },
  methods: {
    getRoomInfo() {
      try { 
      this.$axios
        .get("/api/room/"+this.roomId)
        .then((res) => {
          if (res.status === 200) {
            this.userList = res.data.users;
            this.roomName = res.data.roomName;
            this.ownerId = res.data.ownerId;
          }
        });
      } catch (error) {
        console.log(error);
      }
    },
    connect() {
      const serverURL = "http://localhost:8080/ws-stomp";
      // const serverURL = "http://http://13.209.194.97:8080/ws-stomp"
      let socket = new SockJS(serverURL);
      this.stomp = Stomp.over(socket);
      
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      this.stomp.connect({}, this.subRoom);
    },
    subRoom() {
      this.stomp.subscribe("/sub/room/"+this.roomId, this.subCommand);
      this.enterRoom();
    },
    subCommand(command) {
      try {
        var content = JSON.parse(command.body)
      } catch (error) {
        console.log(error);
      }

      var sender = this.getNickname(content.userId);
      
      if(content.type === "ENTER") {
        console.log("ENTER");
        this.getRoomInfo();
      } else if(content.type === "CHAT") {
        console.log("CHAT");
        this.chatList.push(sender+": "+content.message);
      } else if(content.type === "EXIT") {
        console.log("EXIT");
        this.getRoomInfo();
      } else if(content.type === "START") {
        console.log("START");
        if(content.message === "게임시작") {
          this.$router.push({
            name: 'gameDavinci',
            params: {
              gameId: this.roomId,
            }
          });
        } else {
          console.log("시작불가능");
        }
      }
    },
    enterRoom() {
      for(var i=0; i<this.userList.length; i++) {
        if(this.userList[i].id === this.userId){
          return;
        }
      }
      this.stomp.send("/pub/room", JSON.stringify({userId: this.userId, type: "ENTER", roomId: this.roomId}), {});
    },
    submitChat() {
      this.stomp.send("/pub/room", JSON.stringify({userId: this.userId, type: "CHAT", roomId: this.roomId, message: this.msg}), {});
      this.msg = "";
    },
    exitRoom() {
      this.stomp.send("/pub/room", JSON.stringify({userId: this.userId, type: "EXIT", roomId: this.roomId}), {});
      this.stomp.disconnect();
      this.$router.push("/room-list");
    },
    startGame() {
      // 방장만 실행 가능
      if(this.ownerId === this.userId){
        this.stomp.send("/pub/room", JSON.stringify({userId: this.userId, type: "START", roomId: this.roomId}), {});
      }
    },
    readyClick() {
      this.ready = !this.ready;
    },
    getNickname(id) {
      for(var i=0; i<this.userList.length; i++) {
        if(this.userList[i].id === id){
          return this.userList[i].nickname;
        }
      }
    }
  },
}
</script>

<style>

</style>