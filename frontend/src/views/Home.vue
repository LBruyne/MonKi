<template>
  <div class="home1" style="minHeight: calc(100vh)">
    <video loop autoplay muted style="width:100%;" ref="video" :src="this.videoname">
    </video>
    <div class="loginbutton" style="margin-top:14.5px">
      <div id="div2"><a href="https://movie.douban.com/" style="color: white">Movie</a></div>
      <div id="div3"><a href="https://map.baidu.com/" style="color: white">Location</a></div>
      <div id="div4"><a href="https://music.163.com/" style="color: white">Music</a></div>
      <div id="div5">More</div>
      <div id="div6"><a href="#/search" >search</a></div>
      <div id="div7"><a href="#/result" >result</a></div>
                  <a-icon
              type="user"
              size="large"
              class="searchtext"
              style="float:right;margin-right:49px"
              v-if="isLogin == null"
              @click="showLogin"
            />
            <h1 class="searchtext" style="font-size:15px;float:right;margin-right:49px" @click="showLogout" v-else>
              {{ isLogin }}
            </h1>
    </div>
    

    <div class="body" style="minHeight:100px">
      <!-- 登出框框 -->
      <div ref="replayModal">
      <a-modal v-model="visible_logout" :title="null" :footer="null" :closable="false" :getContainer='()=>$refs.replayModal'>
        <a-icon type="question-circle" class="logouttext"/>
        <div class="logouttext" style="text-align:center;margin-top:5%">Are you sure to Logout?</div>
        <a-button
              size="large"
              type="dashed"
              htmlType="submit"
              class="login-input"
              style="width:100px;margin-top:65px;opacity:30%;margin-left:150px"
              block
              @click="logoutNo"
            >Cancel</a-button>
            <a-button
              size="large"
              type="danger"
              htmlType="submit"
              class="login-input"
              style="width:100px;opacity:50%;margin-left:10px"
              @click="logoutYes"
              block
            >Yes</a-button>
      </a-modal>
      </div>
      <!-- 登入框框 -->
      <div ref="replayModal">
      <a-modal v-model="visible_login" :title="null" :footer="null" :closable="false" :getContainer='()=>$refs.replayModal'>
        <a-form
          id="formLogin"
          class="user-layout-login"
          ref="formLogin"
          :form="form"
          @submit="handleSubmit"
        >
        <a-form-item>
            <a-input
              class="login-input"
              size="large"
              type="text"
              placeholder="Email Address"
              v-decorator="[
                'email',
                {rules: [{ required: true, message: 'Please Enter the Email Address!'}, { validator: checkEmail }], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="mail" :style="{ color: 'rgba(0,0,0,.25)' }"/>
            </a-input>
          </a-form-item>
          <a-form-item>
            <a-input-search
            class="login-input"
              size="large"
              type="text"
              placeholder="ValidCode"
              @search="sendcode"
              v-decorator="[
                'validcode',
                {rules: [{ required: true, message: 'Please Enter the ValidCode!'}], validateTrigger: 'blur'}
              ]"
            >
              <a-icon slot="prefix" type="key" :style="{ color: 'rgba(0,0,0,.25)' }"/>
               <a-button v-if="issend" type="dashed" slot="enterButton">Get ValidCode</a-button>
               <a-button v-if="!issend" disabled slot="enterButton">After {{ count }}s can send again</a-button>
            </a-input-search>
          </a-form-item>

            <a-form-item style="text-align:center">
            <a-button
              size="large"
              type="dashed"
              htmlType="submit"
              class="login-input"
              style="width:120px;"
              block
            >Login</a-button>
          </a-form-item>
        </a-form>
      </a-modal>
      </div>
    </div>
    <div class="search">
      <div class="searchtext">
      <a-icon :type="this.buttontype" @click="stopvideo"/>Search</div>
      <a-input-search class="input-box" placeholder="Please Input Search Text" style="width: 400px" v-model="text" @search="onSearch" size="large"/>
    </div>
    
  <div class="nextbutton">
    <img class="next" src="../assets/right.png" width="40" height="40" alt="button" @click="Getvideourl"/>
  </div>

    <div class="wrapper">
    <div class="mouse-wheel-wrapper" ref="scroll">
      <div class="mouse-wheel-content">
        <div class="mouse-wheel-item" v-for="(item, i) in test" :key=i :style="Getmoviestyle(item, i)" @click="clickTop(item.id, i)">
        <div class="Moviename">
          {{item.name}}
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
// 测试数据
var test=[]
var test1={
  "id":"233",
  "post": require("../assets/Movieback.jpg"),
  "name":"!MovieName!",
  "rating":"9.99",
  "year":"1999",
  "genre":['Action','Amour'],
  "director":"Alan Walker",
  "music":['music1','music2333333333333333333333333333333333333333333333333333333333333333333333333'],
  "visit":['Hz','Sh'],
}
for(var i=0;i<=9;i++)
{
  test1.name = String(i+1) +". "+ "!MovieName!"
  if(i == 2)
    test1.post = "https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png"
  else
    test1.post = require("../assets/Movieback.jpg")
  var test2=JSON.parse(JSON.stringify(test1))
  test.push(test2)
}
var movie = []
var movie1 = {
  height:'120px',
          width: '200px',
          marginRight: '2%',
          fontSize:'16px',
          display:'inline-block',
          textAlign:'center',
          padding:'20 20px',
          lineHeight:'50px',
          borderRadius:'5px 5px 5px 5px',
          background: 'url(require(../assets/Movieback.jpg))',
          backgroundSize: 'cover',
          backgroundPosition: 'center center',
          opacity: '0.8',
          boxShadow: '0px 5px 10px 3px rgba(0, 0, 0, 0.4)',
          transition:'all o.6s',
          filter: 'grayscale(50%)'
}
for(let j=0;j<=9;j++)
{
  var movie2=JSON.parse(JSON.stringify(movie1))
  movie.push(movie2)
}
export default {
  name: "Home",
  components: {},
  data() {
    return {
      visible_login: false,
      visible_logout:false,
      count: 60,
      issend: true,
      priority:0,
      test,
      text:this.$store.state.search.search,
      isLogin: this.$store.state.user.email,
      form: this.$form.createForm(this),
      data:[],
      loading: false,
      busy: false,
      Moviestyle:{
          height:'120px',
          width: '200px',
          marginRight: '2%',
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
          boxShadow: '0px 5px 10px 3px rgba(0, 0, 0, 0.4)',
          transition:'all o.6s',
          filter: 'grayscale(50%)'
      },
      movie,
      backconststyle:'home1',
      number:0,
      number1:0,
      flagvideo: true,
      buttontype:'pause',
      videoname:require('../assets/movie1.mp4'),
      videourl:[require('../assets/movie1.mp4'),require('../assets/girl.mp4'),require('../assets/video.mp4'),require('../assets/harry.mp4'),require('../assets/starwar.mp4')],
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
      Getmoviestyle:function(item, i){
        this.movie[i].background = 'url(' + item.post + ')'
        return this.movie[i];
      },
      onSearch(){
        this.$store.commit('setSearch',this.text)
        console.log(this.text)
        console.log(this.$store.state.search.search)
        this.$router.push('/search')
    },

      sendcode() {
        const TIME_COUNT = 60
        this.form.validateFields(['email'],(emailError,value)=>{
          if(!emailError){
            this.axios.post('/api/app/login/getVerifyCode', {
              email: value.email,
            })
            .then( ()=> {
              window.alert('The ValidCode Has Been Send, Please Check Your Email!')
              if (!this.timer) {
                this.count = TIME_COUNT
                this.issend = false
                this.timer = setInterval(() => {
                  if (this.count > 0 && this.count <= TIME_COUNT) {
                    this.count--;
                  } else {
                    this.issend = true;
                    clearInterval(this.timer);
                    this.timer = null;
                  }
                }, 1000);
              }
            })
            .catch(function (error) {
              console.log(error)
              window.alert('ValidCode Send ERROR!')
            });
          }
          else{
            console.log('********emailError');
          }
        });
      },

    checkEmail (rule, value, callback) {
      const regex = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/
      if (!regex.test(value)) {
        callback('Please Enter the Valid Email!')
      }
      callback()
    },

    handleSubmit (e) {
      e.preventDefault()
      const {
        form: { validateFields },
      } = this
        const validateFieldsKey = ['email', 'validcode']
        validateFields(validateFieldsKey, { force: true }, (err, values) => {
        if (!err) {
          console.log('login form', values)
         this.axios.post('/api/app/login/verify', {
            email: values.email,
            verifyCode:values.validcode,
          })
          .then((response)=> {
            console.log(response);
            
            window.alert(response.data.message)
            if(response.data.code === 0){
                location.reload()
                this.$store.commit('setID',response.data.data)
                this.$store.commit('setEmail',values.email)
                console.log(this.$store.state.user.id)
                console.log(this.$store.state.user.email)
                this.isLogin=this.$store.state.user.email
            }
          })
          .catch(function (error) {
            console.log(error);
          });
        } else {
          console.log("login err")
        }
      })
    },

      showLogin() {
      this.visible_login = true;
      },
      showLogout() {
      this.visible_logout = true;
      },
      logoutYes(){
        this.$store.commit('logout')
        this.visible_logout = false
        location.reload()
      },
      logoutNo(){
        this.visible_logout = false
      },
      Getvideourl(){
        console.log(this.number1);
        this.number1++;
        console.log(this.number1);
        this.videoname = this.videourl[(this.number1)%5];
        console.log(this.videoname);
        const video = this.$refs.video;
        video.play();
      },
      stopvideo(){
        const video = this.$refs.video;
        if(this.flagvideo){
          video.pause();
          console.log("y");
          this.buttontype = "play-circle";
          this.flagvideo = false;
        }
        else if(!this.flagvideo){
          video.play();
          console.log("n");
          this.buttontype = "pause";
          this.flagvideo = true;
        }
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
      },
      getTop()
    {
      this.loading = true;
      // TODO: 获取热搜榜的接口的测试
      this.axios.get('/api/app/engine/recommend',{
        headers:{
          'Authorization':this.$store.state.user.id
        },
        params:{
          'page':1,
          'pageSize':10,
        }
      }).then((res)=>{
        this.loading = false
        console.log(res.data)
        if(res.data.success == true){
          this.test = res.data.data.results
          var relevant = []
          for(let i = 0;i<res.data.data.results.length;i++){
            relevant.push(res.data.data.results[i].id)
          }
          this.$store.state.search.relevant = relevant
        }
        else{
          window.alert(res.data.message)
          console.log(res.data.message)
        }
      }).catch(function (error) {
            console.log(error)
        })
    },
    clickTop(id, i)
    {
      this.$store.state.search.current = i;
      this.$store.commit('setMovieId',id)
      this.$router.push('/result')
      console.log(this.$store.state.search.current)
    },
  },
  mounted(){
    this.init();
    console.log(this.movie)
    this.getTop()
  }
};
</script>

<style scoped>
video{
  position:fixed;
  right:0;
  bottom: 0;
  min-width: 100%;
  min-height: 100%;
  width: auto;
  height: auto;
  z-index: -999;
  transition: all 2s;
}
.home1 {
  background-color: whitesmoke;
  min-width: 100%;
  background-color:black;
  background-repeat: no-repeat;
  background-size: cover;
  background-position: center center;
  filter:brightness(1);
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
  margin-bottom: 80px;
}

.search {
  padding-top: 4.5%;
  padding-left: 10%;
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
  height: 220px;
  margin-top: 7%;
  margin-left: 10%;
}

.mouse-wheel-content{
  display:inline-block;
}

.mouse-wheel-item:hover{
transform: scale(1.3);
-webkit-filter: brightness(2.3);
filter: brightness(2.3);
}

.mouse-wheel-wrapper{
    width:90%;
    white-space:nowrap;
    border-radius:5px;
    overflow:hidden;
    height: 80%;
}  

.Moviename{
    color:aliceblue;
    margin-top: 55%;
    margin-bottom: -3px;
    overflow:hidden;
    white-space:nowrap;
}

.nextbutton{
  padding-top: 0%;
  padding-left: 92%;
  display: inline-block;
  height: 10px;
}

.next:hover{
  transform: scale(1.4);
}

#div1{
  float:right;
}

#div2,#div3,#div4,#div5,#div6,#div7,#div1{
  float:left;
  margin-right: 1.2%;
}

.result {
  width: 100%;
  height: 100%;
  position: relative;
  top: 0;
  left: 0;
  background: url("../assets/Movieback.jpg") no-repeat fixed;
  background-size: cover;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  box-sizing: border-box;
  background-position: center center;
  z-index: 0;
}

.result:after {
  content: "";
  width: 100%;
  height: 100%;
  position: fixed;
  left: 0;
  top: 0;
  background: inherit;
  filter: blur(5px);
  background-color: rgba(141, 141, 141, 0.5);
  background-blend-mode: darken;
  z-index: -100;
}
.head {
  position: relative;
  height: 150px;
  width: 100%;
  display: flex;
  z-index: 10;
}

.left {
  position: absolute;
  top: 10%;
  height:100%;
  left: 55px;
}
.right {
  position: absolute;
  top: 30%;
  width: 20%;
  height: 100%;
  right: 5%;
  text-align: center;
}


.input-box {
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
  opacity: 0.6;
  border-radius: 5px 15px 5px 5px;
  margin-bottom: 20px;
}
.searchtext {
  font-size:20px;
  color: aliceblue;
  bottom: 0;
}
.logouttext {
  font-size:25px;
  color: aliceblue;
  bottom: 0;
}
.login-input {
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
  opacity: 0.7;
  border-radius: 5px 5px 5px 5px;
}
div /deep/ .ant-modal-body{
    position:absolute;
    top:50%;
    left:50%;
    transform:translate(-50%,30%);
    height: 260px;
     width:450px;
     padding:50px 30px 30px;
     background: rgba(0,0,0,.2);
    box-sizing:border-box;
     box-shadow: 0px 15px 25px rgba(0,0,0,.5);
     border-radius:15px;
}
.resultItems{
  float:left;
  width:70%;
}
.hotItems{
  font-size: 20px;
  color: aliceblue;
  float:right;
  width:30%
}
.card{
    vertical-align: top;
    word-spacing:0;
    margin-left: 5%;
    margin-bottom: 40px;
    top: 0;
    width: 90%;
    height: 260px;
    line-height: 20px;
    opacity: 0.8;
    border-radius: 20px;
    box-shadow: 0px 2px 9px 5px rgba(0, 0, 0, 0.4);
    z-index: 100;
}
.card:hover{
  opacity: 1;
  transform: scale(1.05);
}
.card .post{
  padding:10px;
  float: left;
  width: 30%;
  position:relative;
  height: 100%;
  overflow:hidden;
  border-radius: 20px;
  box-shadow: 0px 2px 9px 5px rgba(0, 0, 0, 0.4);
}
.card .post .img{
  border-radius: 20px;
  box-shadow: 0px 2px 9px 5px rgba(0, 0, 0, 0.4);
  position: absolute;
  top: 50%;
  left: 50%;
  display: block;
  min-width: 100%;
  min-height: 100%;
  transform:translate(-50%,-50%);
}
.card .filmtext{
  float: right;
  width: 65%;
}
.card .filmtext .moviename{
  padding-top:30px;
  font-size: 30px;
  color: aliceblue;
  float: left;
  width: 70%;
} 
.card .filmtext .rating{
  padding-top:30px;
  font-size: 20px;
  color: aliceblue;
  float: right;
  padding-left: 20px;
  width: 30%;
} 
.card .filmtext .year{
  padding-top:80px;
  font-size: 20px;
  color: aliceblue;
} 
.card .filmtext .type{
  padding-top:15px;
  font-size: 20px;
  color: aliceblue;
} 
.card .filmtext .director{
  padding-top:15px;
  font-size: 20px;
  color: aliceblue;
} 
.card .filmtext .music{
  padding-top:15px;
  overflow:hidden;
  white-space:nowrap;
  font-size: 20px;
  color: aliceblue;
} 
.card .filmtext .location{
  padding-top:15px;
  font-size: 20px;
  overflow:hidden;
  white-space:nowrap;
  color: aliceblue;
} 

.list{
    vertical-align: top;
    position: fixed;
    word-spacing:0;
    margin-bottom: 10px;
    margin-top:150px;
    top: 0;
    width: 367px;
    height: 430px;
    line-height: 20px;
    opacity: 0.8;
    border-radius: 20px;
    box-shadow: 0px 2px 9px 5px rgba(0, 0, 0, 0.4);
    z-index: 100;
    background-image: url("../assets/monki.png");
    background-size: cover;
    background-position: center center;
    background-color: rgba(0,139,139, 0.6);
    background-blend-mode: darken;
}
.list:hover{
  opacity: 1;
}

/* 分页的输入框 */
div /deep/ .ant-pagination-options-quick-jumper{
  color: aliceblue;
  font-size: 17px;
}
div /deep/ .ant-pagination-options-quick-jumper input {
  background:transparent ;
  color: aliceblue;
  margin-left:15px
}
/* 分页的样式 */
div /deep/ .ant-pagination-prev .ant-pagination-item-link, .ant-pagination-next .ant-pagination-item-link{
  background:transparent ;
  color: aliceblue;
}
div /deep/ .ant-pagination-next .ant-pagination-item-link{
  background:transparent ;
  color: aliceblue;
}
div /deep/ .ant-pagination-item a{
  color: aliceblue;
}
div /deep/ .ant-pagination-item-active a {
    color: #1890ff;
}
div /deep/ .ant-pagination-item {
  background:transparent;
}
div /deep/ .ant-pagination-jump-prev .ant-pagination-item-container .ant-pagination-item-ellipsis{
  color:aliceblue;
}
div /deep/ .ant-pagination-jump-next .ant-pagination-item-container .ant-pagination-item-ellipsis{
  color:aliceblue;
}
</style>