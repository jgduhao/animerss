const App = {
    data() {
      return {
        activeIndex: '1',
        isAnimeListHide: false,
        isRssFeedListHide: true,
        animeTableData: [],
        selectRssFeeds : [],
        nowSelectRssFeed: 0,
        animePage: {
            pageNum: 1,
            pageSize: 10,
            totalElements: 0,
        },
        rssFeedIsUpdating: false,
        rssFeedFormVisible: false,
        rssFeedForm: {
            rssFeedName: '',
            rssFeedUrl: '',
        },
        rssFeedFormRules: {
            rssFeedName: [
                {required: true, message: '请输入订阅名称', trigger: 'blur'},
                {min: 1, max: 50, message: '长度在必须在1到50个字符', trigger: 'blur'},
            ],
            rssFeedUrl: [
                {required: true, message: '请输入订阅地址', trigger: 'blur'},
                {min: 1, max: 500, message: '长度在必须在1到500个字符', trigger: 'blur'},
            ],
        },
        rssFeedFormLabelWidth: '95px',
        rssFeedIsSubmiting: false,
        rssFeedTableData: [],
        rssFeedPage: {
            pageNum: 1,
            pageSize: 10,
            totalElements: 0,
        },
        isSending2Aria: false,
        refeshButtonText: '全部RSS订阅',
        isOpeningRssFeedForm: false,
      };
    },
    mounted() {
        this.requestRssFeedsSelect();
        this.requestGetAminePage();
    },
    methods : {
        handleSelect(key) {
            this.activeIndex = key;
            if(this.activeIndex == '1'){
                this.isAnimeListHide = false;
                this.isRssFeedListHide = true;
                this.nowSelectRssFeed = 0;
                this.requestRssFeedsSelect();
                this.requestGetAminePage();
            }
            if(this.activeIndex == '2'){
                this.isAnimeListHide = true;
                this.isRssFeedListHide = false;
                this.requestRssFeedPage();
            }
        },
        handleAnimeTableSizeChange(val) {
            this.animePage.pageSize = val;
            this.requestGetAminePage();
        },
        handleAnimeTableCurrentChange(val) {
            this.animePage.pageNum = val;
            this.requestGetAminePage();
        },
        updateRssFeed() {
            let vueThis = this;
            let patchPath = "";
            if(this.nowSelectRssFeed == 0){
                patchPath = "anime";
            } else {
                patchPath = "rssFeeds/"+this.nowSelectRssFeed+"/anime";
            }
            this.rssFeedIsUpdating = true;
            axios.patch(patchPath, {}).then(response => {
                vueThis.rssFeedIsUpdating = false;
                let resultMessage = "";
                if(response.data.newCount <= 0){
                    resultMessage = "暂无动画更新";
                } else {
                    resultMessage = '更新了'+response.data.newCount+'条动画记录';
                }
                vueThis.$notify({
                    title: '更新成功',
                    message: resultMessage,
                });
                if(vueThis.nowSelectRssFeed == 0){
                    vueThis.requestGetAminePage();
                } else {
                    vueThis.requestGetOneFeedAnimePage();
                }
            }).catch(error => {
                vueThis.rssFeedIsUpdating = false;
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        addRssFeed(formName) {
            this.clearRssFeedForm();
            this.rssFeedFormVisible = true;
        },
        submitRssFeedForm(formName) {
            let vueThis = this;
            this.$refs[formName].validate((valid) => {
                if(!valid){
                    return false;
                } else {
                    vueThis.rssFeedIsSubmiting = true;
                    if(vueThis.rssFeedForm.rssFeedId == null || vueThis.rssFeedForm.rssFeedId == undefined){
                        axios.put("rssFeeds", vueThis.rssFeedForm).then(response => {
                            vueThis.rssFeedIsSubmiting = false;
                            vueThis.rssFeedFormVisible = false;
                            vueThis.$notify({
                                title: '提示',
                                message: '保存成功',
                            });
                            vueThis.requestRssFeedPage();
                        }).catch(error => {
                            vueThis.rssFeedIsSubmiting = false;
                            vueThis.rssFeedFormVisible = false;
                            let showMsg = '请求失败';
                            if(error.response){
                                showMsg = error.response.data.message;
                            }
                            vueThis.$message({
                                type: 'error',
                                message: showMsg,
                            });
                        });
                    } else {
                        console.log(vueThis.rssFeedForm)
                        axios.patch("rssFeeds", vueThis.rssFeedForm).then(response => {
                            vueThis.rssFeedIsSubmiting = false;
                            vueThis.rssFeedFormVisible = false;
                            vueThis.$notify({
                                title: '提示',
                                message: '保存成功',
                            });
                            vueThis.requestRssFeedPage();
                        }).catch(error => {
                            vueThis.rssFeedIsSubmiting = false;
                            vueThis.rssFeedFormVisible = false;
                            let showMsg = '请求失败';
                            if(error.response){
                                showMsg = error.response.data.message;
                            }
                            vueThis.$message({
                                type: 'error',
                                message: showMsg,
                            });
                        });
                    }               
                }
            });
        },
        cancelRssFeedForm(formName) {
            this.clearRssFeedForm();
            this.$refs[formName].resetFields();
            this.rssFeedFormVisible = false;
        },
        clearRssFeedForm() {
            this.rssFeedForm = {
                rssFeedName: '',
                rssFeedUrl: '',
            };
        },
        editRssFeed(row) {
            let vueThis = this;
            vueThis.isOpeningRssFeedForm = true;
            axios.get("rssFeeds/"+row.rssFeedId).then(response => {
                vueThis.isOpeningRssFeedForm = false;
                vueThis.rssFeedForm = response.data;
                vueThis.rssFeedFormVisible = true;
            }).catch(error => {
                vueThis.isOpeningRssFeedForm = false;
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
            
        },
        deleteRssFeed(row) {
            let vueThis = this;
            this.$msgbox({
                title: '确认',
                message: '确认删除RSS订阅'+row.rssFeedName+'吗？',
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                beforeClose: (action, instance, done) => {
                    if(action == 'confirm'){
                        instance.confirmButtonLoading = true;
                        instance.confirmButtonText = '执行中...';
                        axios.delete("/rssFeeds/"+row.rssFeedId).then(response => {
                            done();
                            instance.confirmButtonLoading = false;
                        }).catch(error => {
                            instance.confirmButtonLoading = false;
                            let showMsg = '请求失败';
                            if(error.response){
                                showMsg = error.response.data.message;
                            }
                            vueThis.$message({
                                type: 'error',
                                message: showMsg,
                            });
                        });
                    } else {
                        done();
                    }
                }
            }).then(action => {
                vueThis.$notify({
                    title: '提示',
                    message: '删除成功',
                });
                vueThis.requestRssFeedPage();
            });
        },
        handleRssFeedTableSizeChange(val) {
            this.rssFeedPage.pageSize = val;
            this.requestRssFeedPage();
        },
        handleRssFeedTableCurrentChange(val) {
            this.rssFeedPage.pageNum = val;
            this.requestRssFeedPage();
        },
        send2Aira(row) {
            let vueThis = this;
            vueThis.isSending2Aria = true;
            axios.put("anime/"+row.animeId+"/aria2DownloadMission", {}).then(response => {
                vueThis.isSending2Aria = false;
                vueThis.$notify({
                    title: '提示',
                    message: "下载任务："+row.animeTitle+" 已发送至Aria2",
                });
            }).catch(error => {
                vueThis.isSending2Aria = false;
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        selectRssFeedChanged(selectId) {
            let vueThis = this;
            if(selectId == 0){
                this.refeshButtonText = '全部RSS订阅';
                this.requestGetAminePage();
            } else {
                this.selectRssFeeds.forEach((rssFeed) => {
                    if(rssFeed.rssFeedId == selectId){
                        vueThis.refeshButtonText = rssFeed.rssFeedName;
                    }
                    this.requestGetOneFeedAnimePage();
                });
            }
        },
        requestGetAminePage() {
            let vueThis = this;
            axios.get("anime?pageSize="+this.animePage.pageSize+"&pageNum="+this.animePage.pageNum).then(response => {
                vueThis.animeTableData = response.data.pageData;
                vueThis.animePage.pageSize = response.data.pageSize;
                vueThis.animePage.pageNum = response.data.pageNum;
                vueThis.animePage.totalElements = response.data.totalElements;
            }).catch(error => {
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        requestGetOneFeedAnimePage() {
            let vueThis = this;
            axios.get("rssFeeds/"+this.nowSelectRssFeed+"/anime?pageSize="+this.animePage.pageSize+"&pageNum="+this.animePage.pageNum).then(response => {
                vueThis.animeTableData = response.data.pageData;
                vueThis.animePage.pageSize = response.data.pageSize;
                vueThis.animePage.pageNum = response.data.pageNum;
                vueThis.animePage.totalElements = response.data.totalElements;
            }).catch(error => {
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        requestRssFeedsSelect() {
            let vueThis = this;
            axios.get("rssFeeds/all").then(response => {
                let defaultSelect = [{rssFeedId: 0, rssFeedName:"全部"}];
                vueThis.selectRssFeeds = defaultSelect.concat(response.data);
            }).catch(error => {
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        requestRssFeedPage() {
            let vueThis = this;
            axios.get("rssFeeds?pageSize="+this.rssFeedPage.pageSize+"&pageNum="+this.rssFeedPage.pageNum).then(response => {
                vueThis.rssFeedTableData = response.data.pageData;
                vueThis.rssFeedPage.pageSize = response.data.pageSize;
                vueThis.rssFeedPage.pageNum = response.data.pageNum;
                vueThis.rssFeedPage.totalElements = response.data.totalElements;
            }).catch(error => {
                let showMsg = '请求失败';
                if(error.response){
                    showMsg = error.response.data.message;
                }
                vueThis.$message({
                    type: 'error',
                    message: showMsg,
                });
            });
        },
        clearRssFeedAnime(row) {
            let vueThis = this;
            this.$msgbox({
                title: '确认',
                message: '确认清空RSS订阅'+row.rssFeedName+'下的动画记录吗？',
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning',
                beforeClose: (action, instance, done) => {
                    if(action == 'confirm'){
                        instance.confirmButtonLoading = true;
                        instance.confirmButtonText = '执行中...';
                        axios.delete("/rssFeeds/"+row.rssFeedId+"/anime").then(response => {
                            done();
                            instance.confirmButtonLoading = false;
                        }).catch(error => {
                            instance.confirmButtonLoading = false;
                            let showMsg = '请求失败';
                            if(error.response){
                                showMsg = error.response.data.message;
                            }
                            vueThis.$message({
                                type: 'error',
                                message: showMsg,
                            });
                        });
                    } else {
                        done();
                    }
                }
            }).then(action => {
                vueThis.$notify({
                    title: '提示',
                    message: '清空成功',
                });
                vueThis.requestRssFeedPage();
            });
        },
    },
  };
  const app = Vue.createApp(App);
  ElementPlus.locale(ElementPlus.lang.zhCn);
  app.use(ElementPlus);
  app.mount("#app");