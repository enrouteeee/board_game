<template>
  <v-container>
    <v-row>
      <v-col cols="1"></v-col>
      <v-col cols="3">
        <v-btn @click.stop="createRoomDialog = true">방만들기</v-btn>
      </v-col>
      <v-col cols="1"></v-col>
      <v-col cols="2">
        <v-btn @click.stop="matchingDialog = true">매칭</v-btn>
      </v-col>
      <v-col cols="1"></v-col>
      <v-col cols="3">
        <v-btn @click="getRoomList">새로고침</v-btn >
      </v-col>
      <v-col cols="1"></v-col>

      <v-col cols="1"></v-col>
      <v-col cols="10">
        <v-data-table
          :headers="headers"
          :items="roomList"
          :items-per-page="itemsPerPage"
          @click:row="clickRoomListRow"
          class="elevation-1">
        </v-data-table>
      </v-col>
      <v-col cols="1"></v-col>

      <v-dialog v-model="createRoomDialog" max-width="400">
        <v-card>
          <v-card-title class="text-h5">방 정보 입력</v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col>
                  <v-text-field
                    label="방제목"
                    required
                    v-model="createRoomName"
                  ></v-text-field>
                </v-col>
                <v-col>
                  <v-select
                    :items="['2', '3', '4']"
                    label="인원수"
                    required
                    v-model="createRoomCapacity"
                  ></v-select>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="green darken-1" text @click=createRoom>만들기</v-btn>
            <v-btn color="green darken-1" text @click="createRoomDialog = false">취소</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>

      <v-dialog v-model="matchingDialog" max-width="400" persistent>
        <v-card>
          <v-card-title v-if="isMatching == false" class="text-h5">매칭 정보</v-card-title>
          <v-card-title v-else class="text-h5">매칭중</v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col v-if="isMatching == false">
                  <v-select
                    :items="['2', '3', '4']"
                    label="인원수"
                    required
                    v-model="matchingCapacity"
                  ></v-select>
                </v-col>
                <v-col v-else>
                  <h1>...</h1>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn v-if="isMatching == false" color="green darken-1" text @click=startMatching>매칭시작</v-btn>
            <v-btn color="green darken-1" text @click=matchingCancel>취소</v-btn>
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
      roomList: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        { text: '방번호', align: 'center', sortable: false, value: 'id'},
        { text: '방이름', align: 'center', sortable: false, value: 'name'},
        { text: '현재인원', align: 'center', sortable: false, value: 'numberOfUsers'},
        { text: '최대인원', align: 'center', sortable: false, value: 'capacity'},
        { text: '상태', align: 'center', sortable: false, value: 'state'},
      ],
      createRoomDialog: false,
      createRoomName: null,
      createRoomCapacity: 2,

      matchingDialog: false,
      matchingCapacity: 2,
      isMatching: false,
    };
  },
  created() {
    this.getRoomList()
  },
  methods: {
    getRoomList() {
      try { 
        this.$axios
          .get("/api/room", {
            headers: {
              'Auth': this.$store.state.token
            }
          })
          .then((res) => {
            if (res.status === 200) {
            console.log(res);
            this.roomList = res.data.list
          }
      });
      } catch (error) {
        console.log(error);
      }
    },
    createRoom() {
      let createRoomData = {}
      createRoomData.name = this.createRoomName;
      createRoomData.capacity = this.createRoomCapacity;

      try { 
        this.$axios
          .post("/api/room", createRoomData, {
            withCredentials: true,
            headers: {
              'Auth': this.$store.state.token
            }
          })
          .then((res) => {
            if (res.status === 200) {
              this.enterRoom(res.data.id);
            }
          });
      } catch (error) {
        console.log(error);
      }
      this.createRoomDialog = false
    },
    enterRoom(id) {
      console.log(id);

      try { 
        this.$axios
          .get("/api/room/"+id+"/enter", {
            headers: {
              'Auth': this.$store.state.token
            }
          })
          .then((res) => {
            if (res.status === 200) {
              if(res.data) {
                this.$router.push({
                  name: 'room',
                  params: {
                  roomId: id,
                  }
                });
              } else {
                console.log("방에 못 들어감");
              }
            }
          });
      } catch (error) {
        console.log(error);
      }
    },
    clickRoomListRow(row) {
      this.enterRoom(row.id);
    },
    startMatching() {
      this.isMatching = true;
      console.log("매칭시작");
      this.connect();
    },
    connect() {
      const serverURL = "http://localhost:8080/ws-stomp";
      // const serverURL = "http://http://13.209.194.97:8080/ws-stomp"
      let socket = new SockJS(serverURL);
      this.stomp = Stomp.over(socket);
      
      console.log(`소켓 연결을 시도합니다. 서버 주소: ${serverURL}`)
      var connectResult = this.stomp.connect({}, this.subMatching);
      console.log("connectResult :", connectResult);
    },
    subMatching() {
      this.stomp.subscribe("/sub/matching/"+this.userId, this.subCommand);
    
      this.stomp.send("/pub/matching",
        JSON.stringify(
          {
            userId:this.$store.state.userId,
            type:"QUEUE",
            gameInfo:"DAVINCI_CODE",
            matchingSystemType:"FIFO",
            capacity:this.matchingCapacity
          }
        ));
    },
    subCommand(command) {
      try {
        var content = JSON.parse(command.body)
      } catch (error) {
        console.log(error);
      }

      console.log("매칭 응답 : ",content);
      this.stomp.disconnect();
      this.isMatching = false;
      this.matchingDialog = false;

      this.$router.push({
                  name: 'room',
                  params: {
                  roomId: content,
                  }
                });
    },
    matchingCancel() {
      this.isMatching = false;
      this.matchingDialog = false;

      if(this.stomp){
        console.log("매칭 중단");
        this.stomp.send("/pub/matching",
        JSON.stringify(
          {
            userId:this.userId,
            type:"CANCEL",
          }
        ));

        this.stomp.disconnect();
      }
    }
  },
}
</script>

<style>

</style>