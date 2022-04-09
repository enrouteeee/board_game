import Vue from 'vue'
import Router from 'vue-router'
import Login from '@/views/LoginView.vue'
import Redirect from '@/views/RedirectView.vue'
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
      path: '/oauth/redirect',
      component: Redirect
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