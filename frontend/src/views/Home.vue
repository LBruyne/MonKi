<template>
  <div :class="this.backconststyle" style="minHeight: calc(100vh)">
    <div class="loginbutton">
      <div id="div2"><a href="https://movie.douban.com/" style="color: white">Movie</a></div>
      <div id="div3"><a href="https://map.baidu.com/" style="color: white">Location</a></div>
      <div id="div4"><a href="https://music.163.com/" style="color: white">Music</a></div>
      <div id="div5">More</div>
      <div id="div1">Login</div>
    </div>
    
    <div class="search">
      <div class="searchtext">
      <a-icon type="instagram"/>Search</div>
      <a-input-search class="input-box" placeholder="Please Input Search Text" style="width: 400px" @search="onSearch" size="large"/>
    </div>
    
  <div class="nextbutton">
    <img src="../assets/right.png" width="40" height="40" alt="button" @click="Getbackstyle"/>
  </div>

    <div class="wrapper">
    <div class="mouse-wheel-wrapper" ref="scroll">
      <div class="mouse-wheel-content">
        <div class="mouse-wheel-item" v-for="n in 50" :key="n" :style="Getmoviestyle(n)">
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
      Moviestyle:{
          height:'120px',
          width: '200px',
          marginRight: '1%',
          fontSize:'16px',
          display:'inline-block',
          textAlign:'center',
          padding:'20 20px',
          lineHeight:'50px',
          borderRadius:'5px 5px 5px 5px',
          background: 'url(../assets/Movieback.jpg)',
          backgroundSize: 'cover',
          backgroundPosition: 'center center',
          opacity: '0.8',
          boxShadow: '0px 5px 10px 3px rgba(0, 0, 0, 0.4)'
      },
      movie:[{
          height:'120px',
          width: '200px',
          marginRight: '1%',
          fontSize:'24px',
          display:'inline-block',
          textAlign:'center',
          padding:'20 20px',
          lineHeight:'50px',
          borderRadius:'5px 5px 5px 5px',
          backgroundImage: 'url(../assets/Movieback.jpg)',
          backgroundSize: 'cover',
          backgroundPosition: 'center center',
          opacity: '0.8',
          boxShadow: '0px 2px 10px 3px rgba(0, 0, 0, 0.4)'
      },
      {
          height:'120px',
          width: '200px',
          marginRight: '1%',
          fontSize:'24px',
          display:'inline-block',
          textAlign:'center',
          padding:'20 20px',
          lineHeight:'50px',
          borderRadius:'5px 5px 5px 5px',
          backgroundColor: 'aqua',
          backgroundSize: 'cover',
          backgroundPosition: 'center center',
          opacity: '0.8',
          boxShadow: '0px 2px 10px 3px rgba(0, 0, 0, 0.4)'
      }
      ],
      backconststyle:'home1',
      number:0,
      backstyle:['home1','home2','home3','home4','home5','home6']
    };
  },
  methods: {
     init() {
        this.bs = new BScroll(this.$refs.scroll, {
          scrollX: true,
          scrollY: false,
          mouseWheel: true
        })
      },
      Getmoviestyle:function(){
        /*
        if(n%2==1){
          console.log(this.movie[0]);
          return this.movie[0];
        }else{
          console.log(this.movie[1]);
          return this.movie[1];
        }*/
        //解决方案 https://www.jianshu.com/p/1bf3ebaddc98
        this.Moviestyle.background = 'url('+require('../assets/Movieback.jpg')+')';
        return this.Moviestyle;
      },
      onSearch(){

      },
      Getbackstyle(){
        console.log(this.backstyle[this.number]);
        if(this.number<6){
          this.number++;
          this.backconststyle = this.backstyle[this.number];
        }
        if(this.number==6){
          this.number = 0;
          this.backconststyle = this.backstyle[this.number];
        }
      }
  },
  mounted(){
    this.init();
  }
};
</script>

<style scoped>
.home1 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/Movieback.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.home2 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/back1.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.home3 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/back7.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.home4 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/back8.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.home5 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/back4.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.home6 {
  background-color: whitesmoke;
  min-width: 100%;
  background-image: url(../assets/back6.jpg);
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
  transition: all 1s;
}
.loginbutton{
  display: inline-block;
  font-size:15px;
  color:aliceblue;
  width: 93.9%;
  padding-top: 2%;
  padding-left: 10%;
  padding-right: 0%;
}
.input-box{
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
  opacity: 0.6;
  border-radius: 5px 15px 5px 5px;
  margin-bottom: 60px;
}

.search {
  padding-top: 10%;
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
  margin-top: 10%;
  margin-left: 10%;
}

.mouse-wheel-content{
  display:inline-block;
}

/*
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
  background: url(../assets/Movieback.jpg);
  background-size: cover;
  background-position: center center;
  opacity: 0.6;
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
}*/

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

.nextbutton{
  padding-top: 0%;
  padding-left: 92%;
  display: inline-block;
  height: 10px;
}

#div1{
  float:right;
}

#div2,#div3,#div4,#div5{
  float:left;
  margin-right: 1.2%;
}
</style>