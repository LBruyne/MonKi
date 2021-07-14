<template>
  <div class="result" style="minHeight: calc(100vh)">
    <div class="head">
      <div class="left">
        <!-- 点击优先级 -->
        <div class="searchtext" >
          <div id="div0" @click="choosePriority('0')"><a style="color: white" ><a-icon type="instagram" /></a></div>
          <div id="div1" @click="choosePriority('1')"><a style="color: white" >Movie</a></div>
          <div id="div2" @click="choosePriority('2')"><a style="color: white">Location</a></div>
          <div id="div3" @click="choosePriority('3')"><a style="color: white" >Music</a></div>
        </div>
        <br>
        <!-- 搜索框 -->
        <a-input-search
          class="input-box"
          placeholder="Please Input Search Text"
          style="width: 400px;"
          v-model="text"
          @search="onSearch"
          size="large"
        />
      </div>
      <div class="right">
        <a-row>
          <!-- 返回主页 -->
          <a-col :span="12">
            <a-icon
              type="home"
              size="large"
              class="searchtext"
              @click="goTo('/home')"
            />
          </a-col>
          <!-- 登录登出 -->
          <a-col :span="12">
            <a-icon
              type="user"
              size="large"
              class="searchtext"
              v-if="isLogin == null"
              @click="showLogin"
            />
            <h1 class="searchtext" style="font-size:15px" @click="showLogout" v-else>
              {{ isLogin }}
            </h1>
          </a-col>
        </a-row>
      </div>
    </div>
    <div class="body" style="minHeight:3100px">
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
      <!-- 搜索结果 -->
      <div class="resultItems">
        <!-- 卡片 -->
        <!-- 这个onmouseover前面加一个冒号就可以了，但是我不知道是实现什么功能的，而且已进入界面就冒出十几个alert -->
        <div class="card" v-for="(item, i) in test" :key=i onmouseover="changebackground()">
          <div class="post">
            <img
              slot="cover"
              class="img"
              alt="example"
              :src= item.post
            />
          </div>
          <div class="filmtext">
            <div class="moviename">{{item.name}}</div>
            <div class="rating">Rating: <a style="color: aqua;opacity:0.6;padding-right:10px">{{item.rating}}</a></div>
            <div class="year">Year: <a style="color: aqua;opacity:0.6;padding-right:10px">{{item.rating}}</a></div>
            <div class="type">Type: <a style="color: aqua;opacity:0.6;padding-right:10px" v-for="(ty,j) in item.genre" :key=j>{{ty}}</a></div>
            <div class="director">Director:  <a style="color: aqua;opacity:0.6;padding-right:10px">{{item.rating}}</a></div>
            <div class="music">Musics:  <a style="color: aqua;opacity:0.6;padding-right:10px;" v-for="(ty,j) in item.music" :key=j>{{ty}}</a></div>
            <div class="location">Locations: <a style="color: aqua;opacity:0.6;padding-right:10px" v-for="(ty,j) in item.visit" :key=j>{{ty}}</a></div>
          </div>
        </div>
        <!-- 分页 -->
        <div style="text-align:center;padding-top:20px" ref="pagination">
        <a-pagination show-quick-jumper :default-current="this.ipagination.current" :total="this.ipagination.total" :pageSize="this.ipagination.pageSize" @change="onChange"
        :getContainer='()=>$refs.pagination' />
        </div>
      </div>
      <!-- 右侧热搜 -->
      <div class="hotItems">
        <div class="list">
          <div class="hotTitle" style="text-align:center;margin-top:20px;font-size:30px;color:#00FFFF">Monki Top Search</div>
          <div class="hotItem" style="margin-left:30px;padding-top:20px;margin-right:30px;overflow:hidden;white-space:nowrap" v-for="(item, k) in test2" :key=k
            @click="clickTop(item.id)">
          {{item.name}}</div>
        </div>
      </div>
      <a-back-top />
    </div>
  </div>
</template>



<script>
// 测试数据
var test=[]
var test1={
  "id":"233",
  "post":"https://gw.alipayobjects.com/zos/rmsportal/JiqGstEfoWAOHiTxclqi.png",
  "name":"!MovieName!",
  "rating":"9.99",
  "year":"1999",
  "genre":['Action','Amour'],
  "director":"Alan Walker",
  "music":['music1','music2333333333333333333333333333333333333333333333333333333333333333333333333'],
  "visit":['Hz','Sh'],
}
for(let i=0;i<=9;i++)
{
  test.push(test1)
}
var test2=JSON.parse(JSON.stringify(test))
for(let i =0;i<test2.length;i++)
{
  test2[i].name = String(i+1)+ ". "+ test2[i].name
}

export default {
  data() {
    return {
      loading:false, 
      visible_login: false,
      visible_logout:false,
      count: 60,//重新发送验证码等待时间
      issend: true,
      priority:this.$store.state.search.priority,//选择的优先级
      text:this.$store.state.search.search,//搜索框的内容
      test,//搜索结果数据
      test2,//热搜数据
      isLogin: this.$store.state.user.email,
      form: this.$form.createForm(this),
      //分页相关信息
      ipagination:{
        current:1,
        pageSize:10,
        showTotal:(total,range)=>{
          return '第 ' + range[0] + ' ~ ' +range[1] + ' 条，共 ' +total + ' 条'
        },
        showQuickJumper:true,
        showSizeChanger:false,
        total:100
      },
    };
  },
  name: "Result",
  components: {},
  methods: {
    goTo(path) {
      this.$router.push(path);
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
    //选择优先级，包括点击时的样式修改
    choosePriority(num){
      console.log(num)
      if(num==0){
        this.divInit()
      }
      else if(num == 1){
        this.divInit()
        document.getElementById('div1').style.fontSize="30px";
        document.getElementById('div0').style.marginTop="10px";
        document.getElementById('div2').style.marginTop="10px";
        document.getElementById('div3').style.marginTop="10px";
        this.$store.commit('setPriority',1)
        this.priority=1
      }
      else if(num == 2){
        this.divInit()
        document.getElementById('div2').style.fontSize="30px";
        document.getElementById('div0').style.marginTop="10px";
        document.getElementById('div1').style.marginTop="10px";
        document.getElementById('div3').style.marginTop="10px";
        this.$store.commit('setPriority',2)
        this.priority=2
      }
      else if(num == 3){
        this.divInit()
        document.getElementById('div3').style.fontSize="30px";
        document.getElementById('div0').style.marginTop="10px";
        document.getElementById('div2').style.marginTop="10px";
        document.getElementById('div1').style.marginTop="10px";
        this.$store.commit('setPriority',3)
        this.priority=3
      }
      console.log(this.priority)
    },
    //优先级归零，即随意搜索，样式归零
    divInit(){
        document.getElementById('div1').style.fontSize="20px";
        document.getElementById('div2').style.fontSize="20px";
        document.getElementById('div3').style.fontSize="20px";
        document.getElementById('div1').style.marginTop="0px";
        document.getElementById('div2').style.marginTop="0px";
        document.getElementById('div3').style.marginTop="0px";
        document.getElementById('div0').style.marginTop="0px";
        this.$store.commit('setPriority',0)
        this.priority=0
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
    changebackground(){
      alert("hahah");
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
    //获取数据接口，第一次进入该页面mounted的时候页数是1，其他时候点击分页的时候会获取页码
    onChange(pageNumber) {
      console.log('Page: ', pageNumber);
      this.loading = true
      // TODO: 获取简略信息的接口的测试
      this.$axios.get('/api/app/engine/search',{
        headers:{
          'token':this.$store.state.user.id
        },
        params:{
          'keyword':this.$store.state.search.search,
          'page':pageNumber,
          'pageSize':this.ipagination.pageSize,
          'type':this.$store.state.search.priority,
        }
      }).then((res)=>{
        this.loading = false
        console.log(res.data)
        if(res.data.success == true){
          this.test = res.data.data.results
        }
        else{
          window.alert(res.data.message)
        }
      }).catch(function (error) {
            console.log(error)
        })
    },
    //结果搜索，实际上并没有接口，而是将搜索内容保存并重新进入、加载本界面，使界面mounted的时候调用onChange接口
    onSearch(){
      this.$store.commit('setSearch',this.text)
      console.log(this.text)
      console.log(this.$store.state.search.search)
      this.$router.push('/search')
      this.ipagination.current = 1
      location.reload()
    },
    //进入该界面的时候就调用该接口获得热搜榜
    getTop()
    {
      // TODO: 获取热搜榜的接口的测试
      this.$axios.get('/api/app/engine/recommend',{
        headers:{
          'token':this.$store.state.user.id
        },
        params:{
          'page':1,
          'pageSize':10,
        }
      }).then((res)=>{
        console.log(res.data)
        if(res.data.success == true){
          this.test2 = res.data.data.results
          for(let i =0;i<this.test2.length;i++){
            this.test2[i].name = String(i+1)+ ". "+ this.test2[i].name
          }
        }
        else{
          window.alert(res.data.message)
          console.log(res.data.message)
        }
      }).catch(function (error) {
            console.log(error)
        })
    },
    //点击榜单的内容跳转到对应的详情界面，详情界面的加载逻辑，也是获取全局state的movieId，然后一进入页面mounted再调用接口
    clickTop(id)
    {
      this.$store.commit('setMovieId',id)
      this.$router.push('/result')
    }
  },
  mounted() {
    console.log(this.$store.state.user.id)
    console.log(this.$store.state.user.email)
    console.log(this.$store.state.search.search)
    console.log(this.$store.state.search.priority)
    this.getTop()
    this.onChange(1)
  },
};
</script>

<style scoped>
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

#div1,#div2,#div3,#div0{
  float:left;
  margin-right: 4%;
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
    height: 460px;
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
.hotItem:hover{
  transform: scale(1.03);
  color: #00FFFF;
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