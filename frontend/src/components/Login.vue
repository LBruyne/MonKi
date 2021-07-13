<template>
  <a-menu theme="light" mode="horizontal" class="logintext">
    <a-sub-menu v-if="showlogin != null">
      <span slot="title" class="submenu-title-wrapper"
        ><a-icon type="user" />
        {{ showlogin }}
      </span>
      <a-menu-item @click="exit()"> 登出 </a-menu-item>
    </a-sub-menu>
    <a-menu-item v-else @click="showModal">
      <a-icon type="user" />
      登录
    </a-menu-item>
  </a-menu>
</template>



<script>
export default {
  name: "Login",
  data() {
    return {
      showlogin: localStorage.getItem("email"),
      visible:false,
    };
  },
  components: {},
  methods: {
    exit() {
      this.$store.commit("logout");
      this.showlogin = null;
    },
    showModal(){
        this.visible=true
    }
  },
  mounted() {},
};
</script>

<style scoped>
.logintext {
  font-size: 20px;
  color: aliceblue;
}
</style>