import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from './user'

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user:user
  }
})

// 使用方法：
// 保存：
// this.$store.commit('setEmail',email)
// this.$store.commit('setID',id)
// 调用：
// this.$store.state.user.email
// this.$store.state.user.id