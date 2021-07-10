<template>
  <div class="home" style ="minHeight: calc(100vh)">
    <div class="search">
      <a-input-search placeholder="Please Input Search Text" style="width: 400px" @search="onSearch" size="large"/>
    </div>
  </div>
</template>



<script>
export default {
  name: "Home",
  components: {},
  methods: {
    
  },
  mounted(){
    
  }
};
</script>

<style scoped>
.home {
  background-color: whitesmoke;
  min-width: 100%;
}

.search {
  padding-top: 12%;
  padding-left: 10%;
}

</style>