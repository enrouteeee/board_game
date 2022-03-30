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
            <div style="height:400px;">
          <div v-for="(item, idx) in chatList" :key="idx">
            <div>{{ item }}</div>
          </div>
          </div>
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
          <v-card-title>{{ item }}</v-card-title>
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
import router from '@/router'

export default {
  data() {
    return {
      nickname: this.$store.state.nickname,
      roomId: this.$route.params.roomId,
      roomName: "",
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
            console.log(res.data);
            this.userList = res.data.nicknames;
            this.roomName = res.data.roomName;
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
      this.stomp.connect({}, this.subRoom);
    },
    send() {
      if(this.stomp && this.stomp.connected) {
        console.log("send");
        let command = {
          sender: this.nickname,
          type: "CHAT",
          roomId: this.roomId
        }
        this.stomp.send("/pub/command/room", JSON.stringify(command), {});
      }
    },
    subRoom() {
      this.stomp.subscribe("/sub/command/room/"+this.roomId, this.subCommand);

      this.stomp.send("/pub/command/room", JSON.stringify({sender: this.nickname, type: "ENTER", roomId: this.roomId}), {});
    },
    subCommand(command) {
      try {
        var content = JSON.parse(command.body)
      } catch (error) {
        console.log(error);
      }
      
      if(content.type === "ENTER") {
        console.log("ENTER");
        // this.userList.push(content.sender);
        this.getRoomInfo();
      } else if(content.type === "CHAT") {
        console.log("CHAT");
        this.chatList.push(content.sender+": "+content.message);
      } else if(content.type === "EXIT") {
        console.log("EXIT");
        this.getRoomInfo();
      } else if(content.type === "START") {
        console.log("START");
        if(content.message === "게임시작") {
          this.$router.push({
            name: 'gameDavinci',
            params: {
            roomId: this.roomId,
            }
          });
        } else {
          console.log("시작불가능");
        }
      }
    },
    submitChat() {
      this.stomp.send("/pub/command/room", JSON.stringify({sender: this.nickname, type: "CHAT", roomId: this.roomId, message: this.msg}), {});
      this.msg = "";
    },
    exitRoom() {
      this.stomp.send("/pub/command/room", JSON.stringify({sender: this.nickname, type: "EXIT", roomId: this.roomId}), {});

      this.$router.push("/room-list");
    },
    startGame() {
      this.stomp.send("/pub/command/room", JSON.stringify({sender: this.nickname, type: "START", roomId: this.roomId}), {});
    },
  },
}
</script>

<style>

</style>