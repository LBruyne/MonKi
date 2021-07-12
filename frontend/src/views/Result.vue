<template>
  <div class="result" style="minHeight: calc(100vh)">
    <div class="content">
    <div class="moviename">
          Call me by your name
    </div>
    <div class="search">
      <a-input-search class="input-box" placeholder="Please Input Search Text" style="width: 400px" @search="onSearch" size="small"/>
    </div>
    <div id="div2"><a href="https://movie.douban.com/" style="color: white">Movie |</a></div>
    <div id="div3"><a href="https://map.baidu.com/" style="color: white">Location</a></div>
    <div id="div4"><a href="https://music.163.com/" style="color: white">Music</a></div>
      <div class="card">
      <div class="text-box">
          <br/>
          <div class="Movietime">Time:</div>
          <div class="Movielocation">Location:</div>
          <div class="Movietype">Type:</div>
          <div class="Movielanguage">Language:</div>
          <div class="Moviedirector">Director:</div>
</div>
    </div>
    <div class="down" :style="{ width: '10%', marginLeft:'2%',paddingTop:'15%' }">
        <a-icon type="chrome" spin :style="{ fontSize: '40px', color: '#ffffff' , marginRight: '50px' ,float:'left' }"/>
    </div>
    </div>



    <div class="article">
      <div class="music" :style="{ width: '10%', marginLeft:'10%', marginTop:'4%', fontSize:'24px',color:'#ffffff'}">
        Music
      </div>
      
      <div class="wrapper">
        <div class="mouse-wheel-wrapper" ref="scroll1">
          <div class="mouse-wheel-content">
            <div class="mouse-wheel-item" v-for="n in 50" :key="n">
             <a style="color: white" href="https://music.163.com/#/search/m/?s=%E4%B8%A4%E5%8F%AA%E8%80%81%E8%99%8E&type=1">
             两只老虎-群星
             </a>
             <a-icon type="link" :style="{ fontSize: '20px', color: '#ffffff' , marginRight: '50px' ,float:'right' }"/>
            </div>
          </div>
        </div>
      </div>

      <div class="poster">
        <div class="main-circle">
            <div class="circle">
                <div class="circle-heart"></div>
            </div>
        </div>        
      </div>

      <div class="Movietext" :style="{ width: '10%', float:'right', marginTop:'10%', fontSize:'30px',color:'#ffffff'}" >
        Location
      </div>
      <div class="wrapper">
        <div class="mouse-wheel-wrapper" ref="scroll2">
          <div class="mouse-wheel-content">
            <div class="mouse-wheel-item" v-for="n in 50" :key="n">
             <a style="color: white" href="https://music.163.com/#/search/m/?s=%E4%B8%A4%E5%8F%AA%E8%80%81%E8%99%8E&type=1">
             两只老虎-群星
             </a>
             <a-icon type="link" :style="{ fontSize: '20px', color: '#ffffff' , marginRight: '50px' ,float:'right' }"/>
            </div>
          </div>
        </div>
      </div>


      <div :style="{backgroundColor:'#000000',height:'400px'}"></div>
    </div>
  </div>
</template>



<script type="text/ecmascript-6">
import BScroll from '@better-scroll/core'
import MouseWheel from '@better-scroll/mouse-wheel'
BScroll.use(MouseWheel)
export default {
  name: "Result",
  components: {},
  methods: {
      init() {
        this.bs = new BScroll(this.$refs.scroll1, {
          mouseWheel: true
        })
        this.bs2 = new BScroll(this.$refs.scroll2, {
          mouseWheel: true
        })
      },
  },
  mounted(){
    this.init();
  }
};
</script>

<style scoped>
.result {
  min-width: 100%;
  background-color:black;
  filter:brightness(1);
}
.article{
  background-color: black;
  height:auto;
  width: 100%;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 2px 4px, rgba(0, 0, 0, 0.3) 0px 7px 13px -3px, rgba(0, 0, 0, 0.2) 0px -3px 0px inset;
}

.content{
  background-image: url("../assets/back6.jpg");
  background-repeat: no-repeat;
  background-size: cover;
  min-width: 100%;
  min-height: 600px;
  width: auto;
  background-position: center center;
  height: auto;
  box-shadow: rgba(0, 0, 0, 0.4) 0px 30px 90px;
}

.moviename{
  font-size: 100px;
  font-family:'Franklin Gothic Medium', 'Arial Narrow', Arial, sans-serif;
  color:aliceblue;
  padding-left: 2%;
  display: inline-block;
  width: 33%;
}

#div4,#div3,#div2{
  float:right;
  margin-right: 1.2%;
  margin-top: 3%;
}


.search {
  margin-top: 3%;
  opacity: 0.5;
  float: right;
  margin-right: 3%;
}

.searchtext{
  font-size: 30px;
  color:aliceblue;
}

.card{
    vertical-align: top;
    word-spacing:0;
    float: right;
    margin-right: 3.5%;
    margin-top: -13%;
    top: 0;
    width: 39%;
    height: 260px;
    line-height: 20px;
    opacity: 0.5;
    border-radius: 20px;
    box-shadow: 0px 2px 9px 5px rgba(0, 0, 0, 0.4);
}

.card:hover{
  opacity: 1;
}

.card .text-box{
    margin-top: 20px;
    margin-left: 4%;
    vertical-align: top;
    margin-top: 40px;
    height: 307px;
    margin-top: 0px;
    display: inline-block;
    text-align: left;
}

.card .text-box .Movietype{
    left: 411px;
    top: 260px;
    width: 186px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}
.card .text-box .Movietime{
    left: 411px;
    top: 213px;
    width: 234px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}

.card .text-box .Movielocation{
    left: 411px;
    top: 308px;
    width: 409px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}

.card .text-box .Movielanguage{
    left: 411px;
    top: 308px;
    width: 409px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}

.card .text-box .Moviedirector{
    left: 411px;
    top: 404px;
    width: 175px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}

.wrapper{
  width: 50%;
  height: 400px;
  margin-top: 1%;
  margin-left: 2%;
  display: inline-block;
}

.mouse-wheel-content{
  display:inline-block;
}

.mouse-wheel-item:hover{
transform: scale(1.1);
-webkit-filter: brightness(2.3);
filter: brightness(2.3);
opacity: 0.8;
box-shadow: 0px 5px 10px 3px rgba(255, 255, 255, 0.3);
}

.mouse-wheel-item{
  height:70px;
  color: aliceblue;
  width: 500px;
  font-size:16px;
  padding-left:1.5%;
  padding-top: 1%;
  text-align:left;
  margin-bottom: 5%;
  background-color: black;
  border-radius: 6px;
  opacity: 0.5;
  box-shadow: 0px 2px 0px 0px rgba(255, 255, 255, 0.3);
}

.mouse-wheel-wrapper{
    height:700px;
    overflow:hidden;
    text-align:center;
} 

.poster{
  height:800px;
  width: 650px;
  float: right;
  overflow:hidden;
}

    .main-circle{
        width: 800px;
        height: 800px;
        /*定义动画效果，对应的"infinite",动画无限次播放，对应的"linear",动画从头到尾的速度是相同的。*/
        animation: circle 6s infinite linear;
        border-radius: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
        background-color: black;
        /*重复径向渐变 从中心开始沿着四周产生渐变效果，模拟出唱片那种感觉，不喜欢可以注释或删掉*/
        background: repeating-radial-gradient(#111 0%, #000 5%);
    }
    .circle{
        width: 400px;
        height: 400px;
        display: flex;
        justify-content: center;
        align-items: center;
        margin: 0 auto;
        border-radius: 100%;
        background-color: red;
        /*看不出旋转效果，所以使用图片*/
        background-image: url('../assets/back6.jpg');
        background-size: cover;
    }
    /*白色圆心，不需要可以直接删除，或者把背景色删掉或改为透明*/
    .circle-heart{
        width: 100px;
        height: 100px;
        margin: 0 auto;
        border-radius: 100%;
        background-color: #FFF;
    }
    @keyframes circle{
        0%{
            /*transform对元素进行旋转、缩放、移动或倾斜。以下就是旋转0度。*/
            transform: rotate(0deg);
        }
        100%{
            /*以下就是旋转360度*/
            transform: rotate(360deg);
        }
    }
</style>