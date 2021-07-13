export default{
    state:{
        search:window.localStorage.getItem('search')?window.localStorage.getItem('search'):null,
    },
    getters:{
        search:(state)=>{
            if(state.search==null){
                let sessionSearch = localStorage.getItem("search");
                if(sessionSearch !=null)
                {
                    state.search = JSON.parse(sessionSearch)
                    return sessionSearch
                }
            }
            return state.search
        },
    },
    mutations:{
        setSearch(state, search){
            localStorage.setItem("search",search)
            state.search=search
        }
    },
    actions:{},
}