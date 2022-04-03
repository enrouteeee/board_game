<template>
  <v-container>
    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="3">
        <v-btn @click.stop="dialog = true">방만들기</v-btn>
      </v-col>
      <v-col cols="2"></v-col>
      <v-col cols="3">
        <v-btn @click="getRoomList">새로고침</v-btn >
      </v-col>
      <v-col cols="2"></v-col>
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
    </v-row>
  </v-container>
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
        { text: '현재인원', align: 'center', sortable: false, value: 'numberOfUsers'},
        { text: '최대인원', align: 'center', sortable: false, value: 'capacity'},
        { text: '상태', align: 'center', sortable: false, value: 'state'},
      ],
      dialog: false,
      createRoomName: null,
      createRoomCapacity: 2,
    };
  },
  created() {
    this.getRoomList()
  },
  methods: {
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
    createRoom() {
      let createRoomData = {}
      createRoomData.name = this.createRoomName;
      createRoomData.capacity = this.createRoomCapacity;

      try { 
        this.$axios
          .post("/api/room", createRoomData, { withCredentials: true })
          .then((res) => {
            if (res.status === 200) {
              this.enterRoom(res.data.id);
            }
          });
      } catch (error) {
        console.log(error);
      }
      this.dialog = false
    },
    enterRoom(id) {
      console.log(id);

      try { 
        this.$axios
          .get("/api/room/"+id+"/enter")
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
    }
  },
}
</script>

<style>

</style>