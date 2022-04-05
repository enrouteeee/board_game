import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'
import axios from 'axios'

Vue.prototype.$axios = axios
// axios.defaults.baseURL = 'http://localhost:8080'
axios.defaults.baseURL = 'http://13.209.194.97:8080'

//vuetify
import Vuetify from 'vuetify'
import 'vuetify/dist/vuetify.min.css'
import 'material-design-icons-iconfont/dist/material-design-icons.css'

export default new Vuetify({
  icons: {
    iconfont: 'md' // 'md' || 'mdi' || 'fa' || 'fa4'
  }
})

Vue.use(Vuetify);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  vuetify: new Vuetify(),
  render: h => h(App),
}).$mount('#app')
