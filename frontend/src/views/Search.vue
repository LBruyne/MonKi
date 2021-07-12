<template>
  <div class="result" style="minHeight: calc(100vh)">
    <div class="head">
      <div class="left">
        <div class="searchtext" >
          <div id="div0" @click="choosePriority('0')" ><a style="color: white" ><a-icon type="instagram" /></a></div>
          <div id="div1" @click="choosePriority('1')"><a style="color: white" >Movie</a></div>
          <div id="div2" @click="choosePriority('2')"><a style="color: white">Location</a></div>
          <div id="div3" @click="choosePriority('3')"><a style="color: white" >Music</a></div>
        </div>
        <br>
        <a-input-search
          class="input-box"
          placeholder="Please Input Search Text"
          style="width: 400px;"
          @search="onSearch"
          size="large"
        />
      </div>
      <div class="right">
        <a-row>
          <a-col :span="12">
            <a-icon
              type="home"
              size="large"
              class="searchtext"
              @click="goTo('/home')"
            />
          </a-col>
          <a-col :span="12">
            <a-icon
              type="user"
              size="large"
              class="searchtext"
              v-if="showLogin != null"
              @click="showModal"
            />
            <h1 class="searchtext" @click="showLogout" v-else>
              {{ showLogin }}
            </h1>
          </a-col>
        </a-row>
      </div>
    </div>
    <div class="body" style="minHeight: calc(100vh - 150px)">
      <div ref="replayModal">
      <a-modal v-model="visible" :title="null" :footer="null" :closable="false" :getContainer='()=>$refs.replayModal'>
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
  </div>
</template>



<script>
export default {
  data() {
    return {
      visible: false,
      count: 60,
      issend: true,
      priority:0,
      showLogin: window.localStorage.getItem("email"),
      form: this.$form.createForm(this),
    };
  },
  name: "Result",
  components: {},
  methods: {
    goTo(path) {
      this.$router.push(path);
    },
    showModal() {
      this.visible = true;
    },
    showLogout() {
      this.$confirm({
        title: "Do you want to log out?",
        onOk() {
          (this.showLogin = null), this.$store.commit("logout");
        },
        onCancel() {},
      });
    },
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
        this.priority=1
      }
      else if(num == 2){
        this.divInit()
        document.getElementById('div2').style.fontSize="30px";
        document.getElementById('div0').style.marginTop="10px";
        document.getElementById('div1').style.marginTop="10px";
        document.getElementById('div3').style.marginTop="10px";
        this.priority=2
      }
      else if(num == 3){
        this.divInit()
        document.getElementById('div3').style.fontSize="30px";
        document.getElementById('div0').style.marginTop="10px";
        document.getElementById('div2').style.marginTop="10px";
        document.getElementById('div1').style.marginTop="10px";
        this.priority=3
      }
      console.log(this.priority)
    },
    divInit(){
        document.getElementById('div1').style.fontSize="20px";
        document.getElementById('div2').style.fontSize="20px";
        document.getElementById('div3').style.fontSize="20px";
        document.getElementById('div1').style.marginTop="0px";
        document.getElementById('div2').style.marginTop="0px";
        document.getElementById('div3').style.marginTop="0px";
        document.getElementById('div0').style.marginTop="0px";
        this.priority=0
    },
    sendcode() {
      const TIME_COUNT = 60
      this.form.validateFields(['email'],(emailError,value)=>{
        if(!emailError){
            // console.log(value.email)
            // TODO: 接口
          this.axios.post('/api/sendCaptcha', {
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
          // TODO: 接口
         this.axios.post('/api/registration', {
            email: values.email,
            auth_code:values.validcode,
          })
          .then((response)=> {
            console.log(response);
            
            window.alert(response.data.message)
            if(response.data.code === 3){
                location.reload()
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
  },
  mounted() {

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
  left: 5%;
}
.right {
  position: absolute;
  top: 30%;
  width: 20%;
  height: 100%;
  right: 0%;
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

.login-input {
  box-shadow: 0px 2px 10px 3px rgba(0, 0, 0, 0.4);
  opacity: 0.6;
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
</style>