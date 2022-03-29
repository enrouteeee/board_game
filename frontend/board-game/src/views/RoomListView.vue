<template>
  <container class="mx-auto">
    <div>
      <h1> room list </h1>
    </div>
    
    <v-col>
      <div>
          <v-btn color="primary" dark @click.stop="dialog = true">방만들기</v-btn>
          <v-dialog v-model="dialog" max-width="400">
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
                <v-btn color="green darken-1" text @click="dialog = false">취소</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-btn @click="getRoomList">새로고침</v-btn >
      </div>
      <div style="height: 15px;"></div>
      <v-data-table
        :headers="headers"
        :items="roomList"
        :items-per-page="itemsPerPage"
        hide-default-footer
        class="elevation-1">
      </v-data-table>
    </v-col>
  </container>
</template>

<script>
export default {
  data() {
    return {
      roomList: [],
      page: 1,
      pageCount: 0,
      itemsPerPage: 10,
      headers: [
        { text: '방번호', align: 'center', sortable: false, value: 'id'},
        { text: '방이름', align: 'center', sortable: false, value: 'name'},
        { text: '최대인원', align: 'center', sortable: false, value: 'capacity'},
        { text: '현재인원', align: 'center', sortable: false, value: 'numberOfUsers'},
      ],
      dialog: false,
      createRoomName: "",
      createRoomCapacity: "",
    };
  },
  methods: {
    createRoom() {
      let createRoomData = {}
      createRoomData.name = this.createRoomName;
      createRoomData.capacity = this.createRoomCapacity;

      try { 
        this.$axios
          .post("/api/room", createRoomData, { withCredentials: true })
          .then((res) => {
            if (res.status === 200) {
              // 방 접속
            }
          });
      } catch (error) {
        console.log(error);
      }
      this.dialog = false
    },
    getRoomList() {
      try { 
        this.$axios
          .get("/api/room")
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
  },
  created() {
    this.getRoomList()
  }
}
</script>

<style>

</style>