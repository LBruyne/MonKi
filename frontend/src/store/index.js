import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

import user from './user'
import search from './search'

export default new Vuex.Store({
  state: {
  },
  mutations: {
  },
  actions: {
  },
  modules: {
    user:user,
    search:search
  }
})

// 使用方法：
// 保存：
// this.$store.commit('setEmail',email)
// this.$store.commit('setID',id)
// this.$store.commit('setSearch,search)
// this.$store.commit('setMovieId',id)
// 调用：
// this.$store.state.user.email         用户的邮箱，用于右上角登陆后显示
// this.$store.state.user.id            用户的id，放在请求头当作token
// this.$store.state.search.search      用户的搜索文本，跳转页面之后自动请求
// this.$store.state.search.movieId     用户的详情选择，跳转页面之后自动请求
