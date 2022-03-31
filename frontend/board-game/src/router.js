import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/LoginView.vue'
import RoomList from '@/views/RoomListView.vue'
import Room from '@/views/RoomView.vue'
import GameDavinci from '@/views/GameDavinci.vue'

Vue.use(Router)

export default new Router({
  mode: 'history',
  routes: [
    {
      path: '/',
      component: Login
    },
    {
      path: '/room-list',
      component: RoomList
    },
    {
      path: '/room/:roomId',
      name: 'room',
      component: Room
    },
    {
      path: '/game/davinci/:gameId',
      name: 'gameDavinci',
      component: GameDavinci
    }
  ]
})