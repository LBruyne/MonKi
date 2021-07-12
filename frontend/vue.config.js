module.exports = {
    devServer:{
        open:true,
        port:8888,
        proxy: {  //配置跨域
          '/api': {
            target: 'http://localhost:8000',
            changOrigin: true,  //允许跨域
            pathRewrite: {
              '^/api': '' 
            }
          },
    },
    }
}