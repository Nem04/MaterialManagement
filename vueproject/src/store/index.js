import vue from 'vue'
import Vuex from 'vuex'
import router,{resetRouter} from "../router";
import createPersistedState from 'vuex-persistedstate'
vue.use(Vuex)

function addRouters(menuList) {
    console.log(menuList)
    let routes = router.options.routes
    console.log(routes)
    routes.forEach(routeItem=>{
        if (routeItem.path=="/Index"){
            menuList.forEach(menu=>{
                let childRoute = {
                    path:'/'+menu.menuclick,
                    name:menu.menuname,
                    meta:{
                        title:menu.menuname
                    },
                    component:()=>import('../components/'+menu.menucomponent)
                }

                routeItem.children.push(childRoute)
            })
        }
    })
    resetRouter()
    router.addRoutes(routes)
    // {
    //     path:'/Admin',
    //         name:'admin',
    //     meta:{
    //     title:'管理员管理'
    // },
    //     component:()=>import('../components/admin/AdminManage.vue')
    // }
    
}

export default new Vuex.Store({
    state:{
        menu:[]
    },
    mutations: {
        setMenu(state,menuList){
            state.menu = menuList
            //添加路由
            addRouters(menuList)
        },
        setRouter(state,menuList){
            addRouters(menuList)
        }
    },
    getters:{
        showNum(state){
            return state.menu;
        }
    },
    plugins:[createPersistedState()]

})