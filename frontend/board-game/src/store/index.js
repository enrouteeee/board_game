import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate';

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    userId: "",
    nickname: ""
  },
  getter: {
  },
  mutations: {
    setNickname: function(state, payload) {
      return state.nickname = payload;
    },
    setUserId: function(state, payload) {
      return state.userId = payload;
    }
  },
  action: {
  },
  plugins: [createPersistedState({
    paths: ["userId", "nickname"]
  })]
})