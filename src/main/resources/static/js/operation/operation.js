var vue = new Vue({
    el: "#app",
    components: {
        'avatar': VueAvatar.Avatar
    },
    data() {
        return {
            isCollapse: false,
            activeNavIndex: "1",
        };
    },
    methods:{
        handleOpen(key, keyPath) {
            console.log(key, keyPath);
        },
        handleClose(key, keyPath) {
            console.log(key, keyPath);
        },
        handleSelect(key, keyPath) {
            console.log(key, keyPath);
        },
        turnToHome() {
            this.$router.replace("/home");
        },
        turnToLogin() {
            this.$router.replace("/login");
        },
        turnToSign() {
            this.$router.replace("/register");
        },
        turnToTemplate() {
            this.$router.replace("/template");
        }
    },
    mounted(){

    }
})