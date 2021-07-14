export default{
    state:{
        search:window.localStorage.getItem('search')?window.localStorage.getItem('search'):undefined,
        movieId:window.localStorage.getItem('movieId')?window.localStorage.getItem('movieId'):undefined,
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
        }
    },
    actions:{},
}