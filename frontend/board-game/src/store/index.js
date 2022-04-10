import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userId: "",
    nickname: "",
    token: ""
  },
  getter: {
  },
  mutations: {
    setNickname: function(state, payload) {
      return state.nickname = payload;
    },
    setUserId: function(state, payload) {
      return state.userId = payload;
    },
    setToken: function(state, payload) {
      return state.token = payload;
    }
  },
  action: {
  },
  plugins: [createPersistedState({
    paths: ["userId", "nickname"]
  })]
})