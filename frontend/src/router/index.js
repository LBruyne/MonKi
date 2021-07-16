import Vue from 'vue'
import VueRouter from 'vue-router'
import Home from '../views/Home.vue'
import Search from '../views/Search.vue'
import Result from '../views/Result.vue'
import Demo from '../views/demo.vue';

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/home',
    name: 'Home',
    component: Home
  },
  {
    path: '/search',
    name: 'Search',
    component: Search
  },
  {
    path: '/result',
    name: 'Result',
    component: Result
  },
  {
    path: '/demo',
    name: 'Demo',
    component: Demo
  },
]

const router = new VueRouter({
  routes
})

export default router
