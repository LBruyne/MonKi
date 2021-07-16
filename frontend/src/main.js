import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import './plugins/ant-design-vue'
import axios from 'axios'
import VueAxios from 'vue-axios'
import anime from 'animejs'

Vue.use(VueAxios,axios)
Vue.prototype.$anime = anime
Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
