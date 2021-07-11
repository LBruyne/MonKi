<template>
  <div class="home" style="minHeight: calc(100vh)">
    <div class="search">
      <div class="searchtext">
      <a-icon type="instagram"/>Search</div>
      <a-input-search class="input-box" placeholder="Please Input Search Text" style="width: 400px" @search="onSearch" size="large"/>
    </div>
    
    <div class="wrapper">
    <div class="mouse-wheel-wrapper" ref="scroll">
      <div class="mouse-wheel-content">
        <div class="mouse-wheel-item" v-for="n in 50" :key="n">
        <div class="Moviename">
          123
        </div>
        </div>
      </div>
    </div>
  </div>



  </div>
</template>



<script type="text/ecmascript-6">
import BScroll from '@better-scroll/core'
import MouseWheel from '@better-scroll/mouse-wheel'
BScroll.use(MouseWheel)
export default {
  name: "Home",
  components: {},
  data() {
    return {
      data:[],
      loading: false,
      busy: false,
    };
  },
  methods: {
     init() {
        this.bs = new BScroll(this.$refs.scroll, {
          scrollX: true,
          scrollY: false,
          mouseWheel: true
        })
      }
  },
  mounted(){
    this.init()
  }
};
</script>

<style scoped>
.home {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/Movieback.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
}

.input-box{
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
  opacity: 0.6;
  border-radius: 5px 15px 5px 5px;
  margin-bottom: 20px;
}

.search {
  padding-top: 12%;
  padding-left: 10%;
}

.searchtext{
  font-size: 30px;
  color:aliceblue;
}
.recommond-box{
  width: 800px;
  height: 200px;
  margin-top: 15%;
  margin-left: 10%;
  background-color: aqua;
}

.wrapper{
  width: 1000px;
  height: 200px;
  margin-top: 15%;
  margin-left: 10%;
}

.mouse-wheel-content{
  display:inline-block;
}

.mouse-wheel-item{
  height:120px;
  width: 200px;
  margin-right: 1%;
  font-size:24px;
  display:inline-block;
  text-align:center;
  padding:20 20px;
  line-height:50px;
  border-radius:5px 5px 5px 5px;
  background-image: url(../assets/Movieback.jpg);
  background-size: cover;
  background-position: center center;
  opacity: 0.6;
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
}

.mouse-wheel-wrapper{
    width:90%;
    white-space:nowrap;
    border-radius:5px;
    overflow:hidden;
}  

.Moviename{
    color:aliceblue;
    margin-top: 55%;
    margin-bottom: -3px;
}

</style>