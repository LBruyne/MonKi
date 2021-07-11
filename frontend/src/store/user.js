import router from '../router'


export default{
    state:{
        email:window.localStorage.getItem('email')?window.localStorage.getItem('email'):null,
        id:window.localStorage.getItem('id')?window.localStorage.getItem('id'):null,
    },
    getters:{
        email:(state)=>{
            if(state.email==null){
                let sessionEmail = localStorage.getItem("email");
                if(sessionEmail !=null)
                {
                    state.email = JSON.parse(sessionEmail)
                    return sessionEmail
                }
            }
            return state.email
        },
        id:(state)=>{
            if(state.id == null)
            {
                let sessionID = localStorage.getItem("id")
                if(sessionID != null)
                {
                    state.id=JSON.parse(sessionID)
                    return sessionID
                }
            }
            return state.id
        }
    },
    mutations:{
        setID(state, id){
            localStorage.setItem("id",id)
            state.id=id
        },
        setEmail(state, email){
            localStorage.setItem("email",email)
            state.id=email
        },
        logout(state){
            state.id=null
            state.email=null
            localStorage.removeItem("id")
            localStorage.removeItem("email")
            router.push('/home')
        }
    },
    actions:{},
}