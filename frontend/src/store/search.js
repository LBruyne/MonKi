export default{
    state:{
        search:window.localStorage.getItem('search')?window.localStorage.getItem('search'):undefined,
    },
    getters:{
        search:(state)=>{
            if(state.search==undefined){
                let sessionSearch = localStorage.getItem("search");
                if(sessionSearch !=undefined)
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