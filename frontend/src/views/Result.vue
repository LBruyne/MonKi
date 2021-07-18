<template>
  <div class="result" style="minHeight: calc(100vh)">
      <div class="down" :style="{ width: '1%',position: 'fixed',wordSpacing:'0',
    marginBottom: '10px',
    marginLeft:'92%',
    marginTop:'550px'}">
        <a-icon type="caret-right" :style="{ fontSize: '40px', color: '#ffffff' , marginLeft: '50px' ,position:'absolute' }" @click="next()"/>
    </div>

    <div class="content">
    <div class="moviename">
          {{this.moviename}}
    </div>

    <div class="search">
      <a-input-search class="input-box" placeholder="Please Input Search Text" style="width: 360px" @search="onSearch" size="small"/>
    </div>
    <div id="div2"><a href="https://movie.douban.com/" style="color: white">Movie |</a></div>
    <div id="div3"><a href="https://map.baidu.com/" style="color: white">Location</a></div>
    <div id="div4"><a href="https://music.163.com/" style="color: white">Music</a></div>

    <a-icon
      type="user"
      size="large"
      id="div5"
      class="searchtext"
      v-if="isLogin == null"
      @click="showLogin"
    />
    <h1 class="searchtext" id="div5" style="font-size:15px" @click="showLogout" v-else>
      {{ isLogin }}
    </h1>

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




      <div class="card">
      <div class="text-box">
          <br/>
          <div class="Movietime">
          {{'Time:'+this.movieyear}}
          </div>
          <div class="Movielocation">{{'Location:'+this.movieylocation}}</div>
          <div class="Movietype">{{'Type:'+this.movietype}}</div>
          <div class="Movielanguage">{{'Rate:'+this.movierate}}</div>
          <div class="Moviedirector">{{'Director:'+this.moviedirector}}</div>
</div>
    </div>
    </div>



    <div class="article">
      <div class="music" :style="{ width: '10%', marginLeft:'10%', marginTop:'4%', fontSize:'24px',color:'#ffffff'}">
        Music
      </div>
      
      <div class="music">
      <div class="wrapper">
        <div class="mouse-wheel-wrapper" ref="scroll1">
          <div class="mouse-wheel-content">
            <div class="mouse-wheel-item" v-for="(item,n) in this.musiclist" :key="n">
             <a style="color: white" :href="'https://music.163.com/#/search/m/?s='+item.name +'&type=1'">
             {{item.name}}<br/>
             <div v-for="i in 1" :key="i">{{item.composer[i]}}</div>
             </a>
             <a-icon type="link" :style="{ fontSize: '20px', color: '#ffffff' , marginRight: '50px' ,float:'right' }"/>
            </div>
          </div>
        </div>
      </div>

      <div class="poster">
        <div class="main-circle">
            <div :style="getbackground1()">
                <div class="circle-heart"></div>
            </div>
        </div>        
      </div>
    </div>

      <div class="Movietext" :style="{ width: '10%', float:'right', marginTop:'10%', fontSize:'30px',color:'#ffffff'}" >
        Location
      </div>
      <div class="picture">
      <div class="wrapper">
        <div class="mouse-wheel-wrapper" ref="scroll2">
          <div class="mouse-wheel-content">
            <div class="mouse-wheel-item1">
              {{this.introduction}}
            </div>
            <div :style="{backgroundColor:'#000000',height:'1px'}"></div>
          </div>
        </div>
      </div>

      <div class="wrapper1">
        <div class="mouse-wheel-wrapper" ref="scroll3">
          <div class="mouse-wheel-content">
            <div class="mouse-wheel-item2" v-for="(item,n) in this.imagelist" :key="n">
              <img :src="getimage(n)" :style="{width: '100%',
    height: '100%',
    objectFit:'cover'}">
            </div>
          </div>
        </div>
      </div>
      </div>
      <div :style="{backgroundColor:'#000000',height:'100px'}"></div>
    </div>
  </div>
</template>



<script type="text/ecmascript-6">
import BScroll from '@better-scroll/core'
import MouseWheel from '@better-scroll/mouse-wheel'
BScroll.use(MouseWheel)
export default {
  data() {
    return {
      visible_login: false,
      visible_logout:false,
      issend: true,
      text:this.$store.state.search.search,
      isLogin: this.$store.state.user.email,
      relevant:this.$store.state.search.relevant,
      current:Number(this.$store.state.search.current),
      form: this.$form.createForm(this),
      data:[],
      loading: false,
      busy: false,
      visible: false,
      count: 60,
      priority:0,
      musiclist:[],
      introduction:'',
      imagelist:[],
      moviename:'',
      movietime:'',
      moviedirector:[],
      movieyear:'',
      movierate:'',
      movietype:[],
      movieylocation:[],
      moviepost:'',
      movie1:{
        backgroundImage: 'url('+ this.moviepost +')',
        backgroundRepeat: 'no-repeat',
        backgroundSize: 'cover',
        minWidth: '100%',
        minHeight: '600px',
        width: 'auto',
        backgroundPosition: 'center center',
        height: 'auto',
        boxShadow: 'rgba(0, 0, 0, 0.4) 0px 30px 90px'
      },
      circle:{
        width: '400px',
        height: '400px',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        margin: '0 auto',
        borderRadius: '100%',
        backgroundColor: 'red',
        /*看不出旋转效果，所以使用图片*/
        backgroundImage: 'url(' + this.moviepost+')',
        backgroundSize: 'cover'
      },
      backgroundurl:''
    };
  },
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
        this.bs3 = new BScroll(this.$refs.scroll3, {
          mouseWheel: true
        })
      },
      getbackground1:function(){
        this.circle.backgroundImage = 'url(' + this.backgroundurl + ')';
        return this.circle;
      },
      getimage(num){
        return this.imagelist[num];
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
      onSearch(){

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
      next:function(){
        if(parseInt(this.current)<this.relevant.length-1)
          this.$store.commit('setCurrent',parseInt(this.current)+1)
        else
          this.$store.commit('setCurrent',parseInt(this.current)-this.relevant.length + 1)
        console.log("hhh");
        location.reload()
      },
      getMessage(){
        this.axios.get('/api/app/movie/get',{
        headers:{
          'Authorization':this.$store.state.user.id
        },
        params:{
          'id':this.relevant[this.current]
        }
      }).then((res)=>{
        if(res.data.success == true){
          this.introduction = res.data.data.location.introduction;
          this.imagelist = res.data.data.location.images;
          this.musiclist = res.data.data.music;
          this.moviename = res.data.data.name;
          this.movieyear = res.data.data.year;
          this.movietype = res.data.data.genre;
          this.movieylocation = res.data.data.location.visit;
          this.movierate = res.data.data.rating;
          this.circle.backgroundImage = res.data.data.post;
          this.movie1.backgroundImage = res.data.data.post;
          this.backgroundurl = res.data.data.post;
          this.moviepost = res.data.data.post;
          console.log(res.data.data.post);
          this.moviedirector = res.data.data.director;
        }
        else{
          window.alert(res.data.message)
        }
      }).catch(function (error) {
            console.log(error)
        })
      }
  },
  mounted(){
    this.init();
    
    this.getMessage();
    
    console.log(this.relevant)
    console.log(this.current)
    console.log(this.relevant[this.current])
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
.music{
  height: auto;
}
.content{
  background-color: #000;
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

#div4,#div3,#div2,#div5{
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

.card{
    vertical-align: top;
    word-spacing:0;
    float: right;
    margin-right: 10%;
    margin-top: -20%;
    top: 0;
    width: 39%;
    height: auto;
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
    height: auto;
    margin-top: 0px;
    display: inline-block;
    text-align: left;
}

.card .text-box .Movietype{
    margin-top: 30px;
    top: 600px;
    left: 411px;
    width: 186px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
    line-height: 150%;
}
.card .text-box .Movietime{
    left: 411px;
    width: 234px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
}

.card .text-box .Movielocation{
    left: 411px;
    margin-top:40px;
    width: 409px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
    line-height: 150%;
}

.card .text-box .Movielanguage{
    left: 411px;
    width: 409px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
    margin-top: 40px;
}

.card .text-box .Moviedirector{
    left: 411px;
    width: 409px;
    height: 50px;
    color: rgba(255, 255, 255, 100);
    font-size: 28px;
    text-align: left;
    line-height: 150%;
}

.wrapper{
  width: 50%;
  height: 400px;
  margin-top: 2%;
  margin-left: 2%;
  display: inline-block;
}

.wrapper1{
  width: 40%;
  margin-top: 2%;
  height: 400px;
  margin-right: 2%;
  display: inline-block;
}

.mouse-wheel-content{
  display:inline-block;
}


.mouse-wheel-item2:hover{
transform: scale(1.1);
-webkit-filter: brightness(1.2);
filter: brightness(1.2);
opacity: 0.8;
box-shadow: 0px 5px 10px 3px rgba(255, 255, 255, 0.3);
}

.mouse-wheel-item2{
  height:300px;
  color: aliceblue;
  width: 500px;
  font-size:16px;
  padding-left:1.5%;
  padding-top: 1%;
  text-align:left;
  margin-bottom: 10%;
  border-radius: 6px;
  opacity: 0.5;
  box-shadow: 0px 2px 0px 0px rgba(255, 255, 255, 0.3);
}

.mouse-wheel-item:hover{
transform: scale(1.1);
-webkit-filter: brightness(2.3);
filter: brightness(2.3);
opacity: 0.8;
box-shadow: 0px 5px 10px 3px rgba(255, 255, 255, 0.3);
}

.mouse-wheel-item{
  height:90px;
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

.mouse-wheel-item1{
  height: auto;
  color: rgb(194, 194, 194);
  width: 600px;
  font-size:16px;
  padding-left:1.5%;
  padding-top: 1%;
  text-align:center;
  margin-bottom: 5%;
  background-color: black;
  border-radius: 6px;
  opacity: 0.5;
  box-shadow: 0px 2px 0px 0px rgba(255, 255, 255, 0.3);
}
.mouse-wheel-item1:hover{
transform: scale(1.1);
-webkit-filter: brightness(2.3);
filter: brightness(2.3);
opacity: 0.8;
box-shadow: 0px 5px 10px 3px rgba(255, 255, 255, 0.3);
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