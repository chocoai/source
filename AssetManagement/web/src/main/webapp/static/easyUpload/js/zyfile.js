var ZYFILE = {
    fileInput: null,
    uploadInput: null,
    dragDrop: null,
    uploadFile: [],
    lastUploadFile: [],
    perUploadFile: [],
    fileNum: 0,
    filterFile: function(files) {
        return files;
    },
    onSelect: function(selectFile, files, newEl) {},
    onDelete: function(file, files) {},
    onProgress: function(file, loaded, total) {},
    onSuccess: function(file, responseInfo) {},
    onFailure: function(file, responseInfo) {},
    onComplete: function(responseInfo) {},
    funDragHover: function(e) {
        e.stopPropagation();
        e.preventDefault();
        this[e.type === "dragover" ? "onDragOver": "onDragLeave"].call(e.target);
        return this;
    },
    funGetFiles: function(e) {
        var self = this;
        this.funDragHover(e);
        var files = e.target.files || e.dataTransfer.files;
        self.lastUploadFile = this.uploadFile;
        this.uploadFile = this.uploadFile.concat(this.filterFile(files));
        var tmpFiles = [];
        var lArr = [];
        var uArr = [];
        $.each(self.lastUploadFile,
            function(k, v) {
                lArr.push(v.name);
            });
        $.each(self.uploadFile,
            function(k, v) {
                uArr.push(v.name);
            });
        $.each(uArr,
            function(k, v) {
                if ($.inArray(v, lArr) < 0) {
                    tmpFiles.push(self.uploadFile[k]);
                }
            });
        this.uploadFile = tmpFiles;
        this.funDealtFiles();
        return true;
    },
    funDealtFiles: function() {
        var self = this;
        $.each(this.uploadFile,
            function(k, v) {
                v.index = self.fileNum;
                self.fileNum++;
            });
        var selectFile = this.uploadFile;
        this.perUploadFile = this.perUploadFile.concat(this.uploadFile);
        this.uploadFile = this.lastUploadFile.concat(this.uploadFile);
        this.onSelect(selectFile, this.uploadFile);
        // console.info("继续选择");
        // console.info(this.uploadFile);
        return this;
    },
    funDeleteFile: function(delFileIndex, isCb, delNameArr) { // delNameArr 以上存名单中删除文件名字
        delNameArr = delNameArr || []
        var self = this;
        var tmpFile = [];
        var delFile = this.perUploadFile[delFileIndex];




        $.each(this.uploadFile,
            function(k, v) {

                if(delNameArr.length > 0){
                    // 删除存在名单
                    if(delNameArr.indexOf(delFile.name) >= 0){
                        delNameArr.splice(delNameArr.indexOf(delFile.name),1)
                    }
                }


                if (delFile != v) {
                    tmpFile.push(v);
                } else {}
            });
        this.uploadFile = tmpFile;
        if (isCb) {
            self.onDelete(delFile, this.uploadFile);
        }
        // console.info("还剩这些文件没有上传:");
        // console.info(this.uploadFile);
        return true;
    },
    funUploadFiles: function() {
        var self = this;
        $.each(this.uploadFile,
            function(k, v) {
                self.funUploadFile(v);
            });
    },
    funUploadFile: function(file) {
        var self = this;

        var formData = new FormData;
        var r = new FileReader()
        r.readAsDataURL(file);

        var getData = {
            url: '' // 阿里云地址
        }


        r.onload = function(e){
            // 获取签名
            $.ajax({
                url: 'https://am.uvanart.com/a/aliyunimage/amAliyunImage/getPolicy',
                type: 'GET',
                contentType: 'text/plain;charset=UTF-8',
                dataType: 'json',
                success: function (res) {
                    getData.url = res.data.host;
                    var flag = false

                    // 提交到阿里云的图片
                    var dataDay = new Date(),
                        dnum = dataDay.getFullYear().toString() + dataDay.getMonth().toString() + dataDay.getDate().toString() + dataDay.getMinutes().toString() + dataDay.getMilliseconds().toString() + dataDay.getSeconds().toString();
                    formData.set('OSSAccessKeyId', res.data.accessId)
                    formData.set('policy', res.data.policy)
                    formData.set('Signature', res.data.signature)
                    formData.set('key', res.data.dir + dnum + file.name)
                    formData.set('success_action_status', res.code)
                    formData.set('callback', res.data.callBack)

                    if(file.type.split('/')[1] === 'pdf'){
                        formData.set('file', dataURLtoBlob(e.target.result))
                        subAliAjax(function(c){
                            if(c.code=== 200){
                                $.ajax({
                                    url: self.url,
                                    type: "POST",
                                    data: JSON.stringify(c.info),
                                    dataType:'json',
                                    contentType:"text/plain;charset=UTF-8",
                                    success: function (res) {
                                        if(res.result === "true"){
                                            self.funDeleteFile(file.index, false);
                                            self.onSuccess(file, res);
                                            if (self.uploadFile.length == 0) {
                                                self.onComplete("全部完成");
                                            }
                                        }else{
                                            layer.msg('提交失败')
                                            self.onFailure(file, err);
                                        }

                                    },
                                    error:function(err){
                                        self.onFailure(file, err);
                                    }
                                });
                            }else{
                                layer.closeAll();
                            }
                        })
                    }else{
                        // 压缩图片
                        canvasDataURL(e.target.result,file.type,function(base_64){
                            formData.set('file', dataURLtoBlob(base_64))
                            subAliAjax(function(c){
                                if(c.code=== 200){
                                    $.ajax({
                                        url: self.url,
                                        type: "POST",
                                        data: JSON.stringify(c.info),
                                        dataType:'json',
                                        contentType:"text/plain;charset=UTF-8",
                                        success: function (res) {
                                            if(res.result === "true"){
                                                self.funDeleteFile(file.index, false);
                                                self.onSuccess(file, res);
                                                if (self.uploadFile.length == 0) {
                                                    self.onComplete("全部完成");
                                                }
                                            }else{
                                                layer.msg('提交失败')
                                                self.onFailure(file, err);
                                            }

                                        },
                                        error:function(err){
                                            self.onFailure(file, err);
                                        }
                                    });
                                }else{
                                    layer.closeAll();
                                }
                            })
                        })
                    }
                }
            })
        }





        // 转二进制
        function dataURLtoBlob(dataurl) {
            var arr = dataurl.split(','), mime = arr[0].match(/:(.*?);/)[1],
                bstr = atob(arr[1]), n = bstr.length, u8arr = new Uint8Array(n);
            while(n--){
                u8arr[n] = bstr.charCodeAt(n);
            }
            return new Blob([u8arr], {type:mime});
        }

        // 图片压缩
        function canvasDataURL(path,imgType,callback){
            var img = new Image();
            img.src = path

            img.onload = function(){
                var that = this;
                // 默认按比例压缩
                var w = that.width,   // 当前图片宽度
                    h = that.height,  // 当前图片高度
                    scale = w / h;  // 比例


                w = (that.width > 1024 ? 1024 : that.width)
                h = w / scale;

                var quality = 0.7;  // 默认图片质量为0.7
                //生成canvas
                var canvas = document.createElement('canvas');
                var ctx = canvas.getContext('2d');
                // 创建属性节点
                var anw = document.createAttribute("width");
                anw.nodeValue = w;
                var anh = document.createAttribute("height");
                anh.nodeValue = h;
                canvas.setAttributeNode(anw);
                canvas.setAttributeNode(anh);
                ctx.drawImage(that, 0, 0, w, h);


                // quality值越小，所绘制出的图像越模糊
                var base64 = canvas.toDataURL(imgType, quality);
                // 回调函数返回base64的值
                callback(base64);
            }
        }


        // 阿里云提交
        function subAliAjax(callback){
            if(getData.url){
                $.ajax({
                    type: "POST",
                    url: getData.url,   // 阿里云上传地址
                    data: formData,
                    processData: false,
                    cache: false,
                    async: true,
                    contentType: false,
                    dataType: "json",
                    header: {
                        'content-type': "multipart/form-data"
                    },
                    success: function (res) {
                        var obj = {
                            info : [{
                                effectiveDate : file.effectiveDate + ' 00:00:00',
                                expireDate :  (file.expireDate === '' ? "" : file.expireDate + ' 00:00:00'),
                                typeName :  file.typeName,
                                profileSurfix :  '.'+file.type.split('/')[1],
                                qualificationCode :  "",
                                qualificationName :  "",
                                savePath :  "",
                                isNeverExpired :  "",
                                supplierCode :  file.supSupplierCode,
                                savePath : getData.url+res.data,
                                qualificationName: res.data
                            }],
                            code: 200
                        }


                        callback(obj)
                    },
                    error: function(res){
                        layer.msg('文件上传失败,请重新尝试')
                        callback({code: 400})
                    }
                });

            }
        }


    },
    funReturnNeedFiles: function() {
        return this.uploadFile;
    },
    init: function() {
        var self = this;
        if (this.dragDrop) {
            this.dragDrop.addEventListener("dragover",
                function(e) {
                    self.funDragHover(e);
                },
                false);
            this.dragDrop.addEventListener("dragleave",
                function(e) {
                    self.funDragHover(e);
                },
                false);
            this.dragDrop.addEventListener("drop",
                function(e) {
                    self.funGetFiles(e);
                },
                false);
        }
        if (self.fileInput) {
            this.fileInput.addEventListener("change",
                function(e) {
                    self.funGetFiles(e);
                },
                false);
        }
        if (self.uploadInput) {
            this.uploadInput.addEventListener("click",
                function(e) {
                    self.funUploadFiles(e);
                },
                false);
        }
    }
};