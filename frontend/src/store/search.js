export default{
    state:{
        search:window.localStorage.getItem('search')?window.localStorage.getItem('search'):undefined,
        movieId:window.localStorage.getItem('movieId')?window.localStorage.getItem('movieId'):undefined,
        priority:window.localStorage.getItem('priority')?window.localStorage.getItem('priority'):0,
        current:window.localStorage.getItem('current')?window.localStorage.getItem('current'):Number(0),
        relevant:JSON.parse(window.localStorage.getItem('relevant'))?JSON.parse(window.localStorage.getItem('relevant')):[],
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
        movieId:(state)=>{
            if(state.movieId==undefined){
                let sessionMovieId = localStorage.getItem("movieId");
                if(sessionMovieId !=undefined)
                {
                    state.movieId = JSON.parse(sessionMovieId)
                    return sessionMovieId
                }
            }
            return state.movieId
        },
    },
    mutations:{
        setSearch(state, search){
            localStorage.setItem("search",search)
            state.search=search
        },
        setMovieId(state, movieId){
            localStorage.setItem("movieId",movieId)
            state.movieId=movieId
        },
        setPriority(state, priority){
            localStorage.setItem("priority",priority)
            state.priority=priority
        },
        setCurrent(state, current){
            localStorage.setItem("current",current)
            state.current=current
        },
        setRelevant(state, relevant){
            localStorage.setItem("relevant",JSON.stringify(relevant))
            state.relevant=relevant
        },
        init(){
            localStorage.removeItem('relevant')
        }
    },
    actions:{},
}