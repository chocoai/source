/*
www.198zone.com
*/

(function ($, undefined) {
    $.fn.zyUpload = function (options, param) {
        var otherArgs = Array.prototype.slice.call(arguments, 1);
        if (typeof options == 'string') {
            var fn = this[0][options];
            if ($.isFunction(fn)) {
                return fn.apply(this, otherArgs);
            } else {
                throw ("zyUpload - No such method: " + options);
            }
        }

        return this.each(function () {
            var para = {};    // 保留参数
            var self = this;  // 保存组件对象

            var defaults = {
                width: "700px",  					// 宽度
                height: "400px",  					// 宽度
                itemWidth: "140px",                     // 文件项的宽度
                itemHeight: "120px",                     // 文件项的高度
                url: "",  	// 上传文件的路径
                multiple: false,  						// 是否可以多个文件上传
                hasFileINBase: false,
                baseUrl: '',
                delloadFile: [],                        // 需要删除的文件
                dataInfo: [],                           // 数据
                dragDrop: true,  						// 是否可以拖动上传文件
                upLoadType: '',  						// 当前上传文件/图片格式
                upLoadName: '',  						// 当前上传后插入的位置
                del: true,  						    // 是否可以删除文件
                finishDel: false,  						// 是否在上传文件完成后删除预览
                uplodFailure: '',                       // 上传文件失败的文件名字信息存储
                addHaveFiles: [],   // 关闭上传加载
                /* 提供给外部的接口方法 */
                onSelect: function (selectFiles, files) { },// 选择文件的回调方法  selectFile:当前选中的文件  allFiles:还没上传的全部文件
                onDelete: function (file, files) { },     // 删除一个文件的回调方法 file:当前删除的文件  files:删除之后的文件
                onSuccess: function (file) { },            // 文件上传成功的回调方法
                onFailure: function (file) { },            // 文件上传失败的回调方法
                onComplete: function (responseInfo) { },    // 上传完成的回调方法
            };

            para = $.extend(defaults, options);

            this.init = function () {
                this.createHtml();  // 创建组件html
                this.createCorePlug(false);  // 调用核心js
            };

            /**
             * 功能：创建上传所使用的html
             * 参数: 无
             * 返回: 无
             */
            this.createHtml = function () {
                var multiple = "";  // 设置多选的参数
                para.multiple ? multiple = "multiple" : multiple = "";
                var html = '';


                // 企业经营范围软体/软木 环评报告可选
                var val229 = $('#businessScope').val(),   // 0 木器  1 软体  2软木
                    html229 = $('#businessScope').find('option[value='+val229+']').html(),
                    huanbao = '<div name="pdf-1" tit="3" type="pdf" class="def_upload important"><div class="filePicker">上传环评报告证明(必传/pdf)</div></div>';
                if((val229 === "1" && html229 === '软体') || (val229 === "2" && html229 === '软木')) huanbao = '<div name="pdf-1" tit="3" type="pdf" class="def_upload"><div class="filePicker">上传环评报告证明(可选/pdf)</div></div>';



                // 默认加载html(新建表单)
                var defHtml =  '<div name="fading" tit="1" type="img" class="def_upload important"><div class="filePicker">上传法人身份证(必传/图片)</div></div>';
                defHtml += '<div name="yingye" tit="2" type="img" class="def_upload important"><div class="filePicker">上传营业执照(必传/图片)</div></div>';
                defHtml += huanbao;
                defHtml += '<div name="pdf-2" tit="4" type="pdf" class="def_upload important"><div class="filePicker">上传消防验证报告(必传/pdf)</div></div>';
                defHtml += '<div name="pdf-3" tit="5" ></div>';
                defHtml += '<div name="pdf-4" tit="6" ></div>';




                if (para.dragDrop) {   // 不开启拖动
                    // 创建带有拖动的html
                    html += '<form id="uploadForm" action="' + para.url + '" method="post" enctype="multipart/form-data">';
                    html += '	<div class="upload_box">';
                    html += '		<div class="upload_main">';
                    html += '			<div class="upload_choose">';
                    html += '				<div class="convent_choice">';
                    html += '					<div class="andArea">';
                    html += '						<div class="filePicker">点击选择文件</div>';
                    html += '						<input id="fileImage" type="file" size="30" name="fileselect[]" ' + multiple + '>';
                    html += '					</div>';
                    html += '				</div>';
                    html += '				<span id="fileDragArea" class="upload_drag_area">或者将文件拖到此处</span>';
                    html += '			</div>';
                    html += '			<div class="status_bar">';
                    // html += '				<div id="status_info" class="info">选中0张文件，共0B。</div>';
                    html += '				<div class="btns">';
                    // html += '					<div class="webuploader_pick">继续选择</div>';
                    html += '					<div class="upload_btn">开始上传</div>';
                    html += '				</div>';
                    html += '			</div>';
                    html += '			<div id="preview" class="upload_preview"></div>';
                    html += '		</div>';
                    html += '		<div class="upload_submit">';
                    html += '			<button type="button" id="fileSubmit" class="upload_submit_btn">确认上传文件</button>';
                    html += '		</div>';
                    html += '		<div id="uploadInf" class="upload_inf"></div>';
                    html += '	</div>';
                    html += '</form>';
                } else {
                    var imgWidth = parseInt(para.itemWidth.replace("px", "")) - 15;

                    // 创建不带有拖动的html
                    html += '<form id="uploadForm" action="' + para.url + '" method="post" enctype="multipart/form-data">';
                    html += '	<div class="upload_box">';
                    html += '		<div class="upload_main single_main">';
                    html += '			<div class="status_bar">';
                    html += '				<div id="status_info" class="info">选中0张文件，共0B。</div>';
                    html += '				<div class="btns">';
                    html += '					<input id="fileImage" type="file" size="30" name="fileselect[]" ' + multiple + '>';
                    html += '					<div class="webuploader_pick">选择文件</div>';
                    html += '					<div class="upload_btn">开始上传</div>';
                    html += '				</div>';
                    html += '			</div>';
                    html += '			<div id="preview" class="upload_preview">';
                    html += defHtml
                    html += '			</div>';
                    html += '		</div>';
                    html += '		<div class="upload_submit">';
                    html += '			<button type="button" id="fileSubmit" class="upload_submit_btn">确认上传文件</button>';
                    html += '		</div>';
                    html += '		<div id="uploadInf" class="upload_inf"></div>';
                    html += '	</div>';
                    html += '</form>';
                }



                $(self).append(html).css({ "width": para.width, "height": para.height });
                // 遍历数据库已有数据
                if(para.dataInfo.length > 0){
                    var json = JSON.parse(para.dataInfo)
                    console.log(json)
                    for(var a in json){
                        // 判断当前添加图片所在坑的名字显示
                        var fileName = '',  // 标题
                            loadHtml = '',
                            el = $('.def_upload[tit="'+json[a].typeName+'"]') // 需要插入的坑

                        var fileSrc = json[a].profileSurfix === '.pdf' ? '/static/easyUpload/images/fileType/pdf2.png' : json[a].savePath
                        switch (json[a].typeName) {
                            case '1':  // 法人身份证
                                fileName = '法人身份证'
                                break;
                            case '2':  // 营业执照
                                fileName = '营业执照'
                                break;
                            case '3': // 环评报告证明
                                fileName = '环评报告证明'
                                break;
                            case '4': // 消防验证报告
                                fileName = '消防验证报告'
                                break;
                            case '5': // 产品检测报告
                                fileName = '产品检测报告'
                                break;
                            case '6': // 排污许可证
                                fileName = '排污许可证'
                                break;

                            default:
                                break;
                        }

                        loadHtml = '<div id="uploadList_' + json[a].qualificationCode + '" class="upload_append_list" hasBase="true">';
                        loadHtml += '			<p class="imgName">' + fileName + '</p>';
                        loadHtml += '	<div class="file_bar">';
                        loadHtml += '		<div style="padding:5px;">';
                        loadHtml += '		</div>';
                        loadHtml += '	</div>';
                        loadHtml += '	<a style="height:' + para.itemHeight + ';width:' + para.itemWidth + ';" class="imgBox" data-id="'+json[a].qualificationCode+'">';
                        loadHtml += '       <div class="hoverBg"><span class="file_see file_set" data-index="'+json[a].qualificationCode+'">查看</span><span class="file_set file_down" data-index="'+json[a].qualificationCode+'">下载</span></div>';  // hasBase 在数据库有此数据
                        loadHtml += '		<div class="uploadImg" style="width:' + imgWidth + 'px">';
                        loadHtml += '			<img id="uploadImage_' + json[a].qualificationCode + '"  class="upload_image"  src="' + fileSrc + '" down="'+json[a].savePath+'" style="" />';
                        loadHtml += '            </div>'
                        loadHtml += '		</div>';
                        loadHtml += '	</a>';
                        loadHtml += '</div>';


                        el.append(loadHtml).addClass('nothing');
                        el.find('.filePicker').hide()



                        // 添加有效日期
                        if(el.find('.selData').length === 0){
                            var d = $('.copyData').html()
                            el.find('.uploadImg').append(d)
                            el.find('.dataEF').val(json[a].effectiveDate.split(' ')[0]).attr('disabled','disabled')
                            el.find('.dataEX').val(json[a].expireDate.split(' ')[0]).attr('disabled','disabled')
                        }

                    }
                }



                // // 初始化html之后绑定按钮的点击事件
                this.addEvent();
                this.createCorePlug(true)
            };

            /**
             * 功能：显示统计信息和绑定继续上传和上传按钮的点击事件
             * 参数: 无
             * 返回: 无
             */
            this.funSetStatusInfo = function (files) {
                var size = 0;
                var num = files.length;
                $.each(files, function (k, v) {
                    // 计算得到文件总大小
                    size += v.size;
                });

                // 转化为kb和MB格式。文件的名字、大小、类型都是可以现实出来。
                if (size > 1024 * 1024) {
                    size = (Math.round(size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
                } else {
                    size = (Math.round(size * 100 / 1024) / 100).toString() + 'KB';
                }

                // 设置内容
                $("#status_info").html("选中" + num + "张文件，共" + size + "。");
            };

            /**
             * 功能：过滤上传的文件格式等
             * 参数: files 本次选择的文件
             * 返回: 通过的文件
             */
            this.funFilterEligibleFile = function (files) {

                //加载
                layer.load(1, {
                    title: '上传中...',
                    shade: [0.1,'#000'] //0.1透明度的白色背景
                });

                var arrFiles = [];  // 替换的文件数组


                // 判断当前一次性上传文件的数量: 比传限定每次选择1个文件 / 其他最多2个 共6个
                if (para.upLoadName === '' && files.length > 2) { layer.msg('额外文件最多只能上传2份') }
                else if (para.upLoadName !== '' && files.length > 1) { layer.msg('请选择一份文件上传') }

                else {
                    for (var i = 0, file; file = files[i]; i++) {
                        if(para.upLoadType === 'img' && file.type.indexOf("png") < 0 && file.type.indexOf("jpg") < 0 && file.type.indexOf("png") < 0 && file.type.indexOf("jpeg") < 0 && file.type.indexOf("bmp") < 0){
                            layer.closeAll()
                            layer.msg('图片格式不对，只能上传img/png/jpg/bmp的图片');

                            continue;
                        }

                        if (para.upLoadType === 'pdf' && (file.type.indexOf("pdf") < 0)) {
                            layer.closeAll()
                            layer.msg('pdf格式不对，只能上传pdf文件');
                            continue;
                        }

                        if (file.size >= 20971520) {  // 20m
                            layer.closeAll()
                            layer.msg(file.name + '"文件大小过大（限制20MB大小）');
                        } else {
                            // 在这里需要判断当前所有文件中
                            arrFiles.push(file);

                            if(para.addHaveFiles.indexOf(file.name) < 0) para.addHaveFiles.push(file.name)
                            else{layer.closeAll(); layer.msg('请勿重复上传文件')}
                        }
                    }
                }
                return arrFiles;
            };

            /**
             * 功能： 处理参数和格式上的预览html
             * 参数: files 本次选择的文件
             * 返回: 预览的html
             */
            this.funDisposePreviewHtml = function (file, e) {
                var html = "";
                var imgWidth = parseInt(para.itemWidth.replace("px", "")) - 15;

                // 处理配置参数删除按钮
                var delHtml = "";
                if (para.del) {  // 显示删除按钮
                    delHtml = '';
                }

                // 处理不同类型文件代表的图标
                var fileImgSrc = "/static/easyUpload/images/fileType/";
                if (file.type.indexOf("rar") > 0) {
                    fileImgSrc = fileImgSrc + "rar.png";
                } else if (file.type.indexOf("zip") > 0) {
                    fileImgSrc = fileImgSrc + "zip.png";
                } else if (file.type.indexOf("text") > 0) {
                    fileImgSrc = fileImgSrc + "txt.png";
                }else if (file.type.indexOf("pdf") > 0) {
                    fileImgSrc = fileImgSrc + "pdf2.png";
                }
                else {
                    fileImgSrc = fileImgSrc + "file.png";
                }


                // 判断当前添加图片所在坑的名字显示
                var fileName = ''
                switch (para.upLoadName) {
                    case 'fading':  // 法人身份证
                        fileName = '法人身份证'
                        break;
                    case 'yingye':  // 营业执照
                        fileName = '营业执照'
                        break;
                    case 'pdf-1': // 环评报告证明
                        fileName = '环评报告证明'
                        break;
                    case 'pdf-2': // 消防验证报告
                        fileName = '消防验证报告'
                        break;
                    case 'pdf-3': // 产品检测报告
                        fileName = '产品检测报告'
                        break;
                    case 'pdf-4': // 排污许可证
                        fileName = '排污许可证'
                        break;

                    default:
                        // $("#preview").append(html);
                        break;
                }


                // 图片上传的是图片还是其他类型文件
                if (file.type.indexOf("image") == 0) {
                    html += '<div id="uploadList_' + file.index + '" class="upload_append_list">';
                    html += '			<p class="imgName">' + fileName + '</p>';
                    html += '	<div class="file_bar">';
                    html += '		<div style="padding:5px;">';
                    //html += delHtml;   // 删除按钮的html
                    html += '		</div>';
                    html += '	</div>';
                    html += '	<a style="height:' + para.itemHeight + ';width:' + para.itemWidth + ';" class="imgBox" data-id="'+file.index+'">';
                    html += '       <div class="hoverBg"><span class="file_set file_see"  data-index="'+file.index+'">查看</span><span class="file_set file_down" data-index="'+file.index+'">下载</span></div>';
                    html += '		<div class="uploadImg" style="width:' + imgWidth + 'px">';
                    html += '			<img id="uploadImage_' + file.index + '" class="upload_image"  src="' + e.target.result + '" down="'+e.target.result+'" style="" />';
                    html += '		</div>';
                    html += '	</a>';
                    html += '	<p id="uploadFailure_' + file.index + '" class="file_failure">上传失败！请选择其他文件上传</p>';
                    html += '</div>';

                } else {
                    html += '<div id="uploadList_' + file.index + '" class="upload_append_list">';
                    html += '			<p class="imgName">' + fileName + '</p>';
                    html += '	<div class="file_bar">';
                    html += '		<div style="padding:5px;">';
                    //html += delHtml;   // 删除按钮的html
                    html += '		</div>';
                    html += '	</div>';
                    html += '	<a style="height:' + para.itemHeight + ';width:' + para.itemWidth + ';" class="imgBox" data-id="'+file.index+'">';
                    html += '       <div class="hoverBg"><span class="file_see" data-index="'+file.index+'">查看</span><span class="file_down" data-index="'+file.index+'">下载</span><span class="file_del" data-index="'+file.index+'">删除</span></div>';
                    html += '		<div class="uploadImg" style="width:' + imgWidth + 'px">';
                    html += '			<img id="uploadImage_' + file.index + '" class="upload_image"  src="' + fileImgSrc + '" down="'+e.target.result+'" style="" />';
                    html += '		</div>';
                    html += '	</a>';
                    html += '	<p id="uploadFailure_' + file.index + '" class="file_failure">上传失败！请选择其他文件上传</p>';
                    html += '</div>';
                }

                return html;
            };

            /**
             * 功能：调用核心插件
             * 参数: 无
             * 返回: 无
             */
            this.createCorePlug = function (isDefEl) {
                isDefEl = isDefEl || false

                // 绑定删除按钮事件
                var funBindDelEvent = function () {
                    if ($(".file_del").length > 0) {
                        // 删除方法
                        $(".file_del").click(function () {
                            var _t = $(this)
                            if($(this).attr('hasbase') ||  para.hasFileINBase){
                                para.delloadFile.push(_t.attr('data-index'))
                                if(_t.closest('.def_upload').hasClass('important')) para.hasFileINBase = false
                                var name =  _t.closest('.def_upload').attr('name')
                                $('div[name=' + name + ']').removeClass('nothing');
                                $('div[name=' + name + ']').find('.filePicker').show()
                                _t.closest('.upload_append_list').remove()

                            }else{
                                var name = $(this).closest('.def_upload').attr('name')
                                $('div[name=' + name + ']').removeClass('nothing');
                                $('div[name=' + name + ']').find('.filePicker').show()
                                ZYFILE.funDeleteFile(parseInt($(this).attr("data-index")), true, para.addHaveFiles);
                                $('#fileImage').val('')  // 清空上传


                            }

                            return false;
                        });
                    }

                    if ($(".file_edit").length > 0) {
                        // 编辑方法
                        $(".file_edit").click(function () {
                            // 调用编辑操作
                            ZYFILE.funEditFile(parseInt($(this).attr("data-index")), true);
                            return false;
                        });
                    }
                };

                // 查看文件
                var funBindSeeEvent = function(){
                    if ($(".file_see").length > 0) {
                        $(".file_see").click(function () {
                            var index = $(this).attr("data-index")
                            $('.photoShow .img').attr('src',$('#uploadImage_'+index).attr('src'))
                            $(window).scrollTop(0);
                            $('.photoShow').fadeIn()
                        })
                    }
                }

                // 文件上传样式类
                var uploadStyle = function(){
                    if ($(".upload_image").length > 0) {
                        $('.upload_image').click(function(){
                            $(this).closest('.imgBox').find('.hoverBg').stop().fadeIn()
                        })
                        $('.def_upload').hover(function(e){},function(){
                            $('.hoverBg').stop().fadeOut()
                        })
                    }
                }


                // 下载当前文件
                var funBindDownEvent = function(){
                    if ($(".file_down").length > 0) {

                        // //支持IE11
                        // function downloadFile(fileName, content) {
                        //     var blob = base64Img2Blob(content);
                        //     //支持IE11
                        //     window.navigator.msSaveBlob(blob, fileName);
                        // }



                        // function oDownLoad(url) {
                        //     console.log(myBrowser());

                        //     if (myBrowser() === "IE") { //IE  //|| myBrowser() === "Edge"
                        //         odownLoad.href = "#";

                        //         // SaveAs5(url);
                        //         downloadFile("下载.jpg", url);
                        //     } else { //!IE
                        //         var blob = base64Img2Blob(url);
                        //         url = window.URL.createObjectURL(blob);
                        //         odownLoad.href = url; odownLoad.download = "";
                        //     }
                        // }

                        // var odownLoad = null;

                        // var MIME = {
                        //     "application/x-zip-compressed": "zip",
                        //     "application/javascript": "js",
                        //     "text/css": "css",
                        //     "text/plain": "txt",
                        //     "text/html": "html",
                        //     "text/xml": "xml",
                        //     "image/jpeg": "jpeg",
                        //     "image/png": "png",
                        //     "image/gif": "gif",
                        //     "image/svg+xml": "svg"
                        // };

                        // //文件名默认当前时间戳
                        // filename.value = Date.now();

                        // //检测点击下载按钮
                        // btnGenerate.addEventListener("click", function (e) {
                        //     var fname = filename.value + "." + MIME[getContentType(base64text.value)];
                        //     var blob = getBlob(base64text.value);

                        //     if (navigator.msSaveBlob) {
                        //         navigator.msSaveBlob(blob, fname);
                        //     }
                        //     else {
                        //         btnDownload.download = fname;
                        //         btnDownload.href = URL.createObjectURL(blob);
                        //         btnDownload.click();
                        //     }
                        // });

                        // /**
                        //  * 获取Blob
                        //  * @param {stirng} base64
                        //  */
                        // function getBlob(base64) {
                        //     return b64toBlob(getData(base64), getContentType(base64));
                        // }

                        // /**
                        //  * 获取文件类型
                        //  * @param {string} base64
                        //  */
                        // function getContentType(base64) {
                        //     return /data:([^;]*);/i.exec(base64)[1];
                        // }

                        // /**
                        //  * 获取base64中的数据
                        //  * @param {string} base64
                        //  */
                        // function getData(base64) {
                        //     return base64.substr(base64.indexOf("base64,") + 7, base64.length);
                        // }

                        // /**
                        //  * base64转Blob
                        //  * @param {string} b64Data
                        //  * @param {string} contentType
                        //  * @param {number} sliceSize
                        //  */
                        // function b64toBlob(b64Data, contentType, sliceSize) {
                        //     contentType = contentType || '';
                        //     sliceSize = sliceSize || 512;

                        //     var byteCharacters = atob(b64Data);
                        //     var byteArrays = [];

                        //     for (var offset = 0; offset < byteCharacters.length; offset += sliceSize) {
                        //         var slice = byteCharacters.slice(offset, offset + sliceSize);

                        //         var byteNumbers = new Array(slice.length);
                        //         for (var i = 0; i < slice.length; i++) {
                        //             byteNumbers[i] = slice.charCodeAt(i);
                        //         }

                        //         var byteArray = new Uint8Array(byteNumbers);

                        //         byteArrays.push(byteArray);
                        //     }

                        //     var blob = new Blob(byteArrays, { type: contentType });
                        //     return blob;
                        // }

                        $(".file_down").off().click(function () {
                            var index = $(this).attr("data-index"),
                                imgSrc = $('#uploadImage_'+index).attr('down')

                            var $a = document.createElement('a');
                            $a.setAttribute("href", imgSrc);
                            $a.setAttribute("download", "");
                            var evObj = document.createEvent('MouseEvents');
                            evObj.initMouseEvent( 'click', true, true, window, 0, 0, 0, 0, 0, false, false, true, false, 0, null);
                            $a.dispatchEvent(evObj);

                            // oDownLoad(imgSrc,$('.file_del[data-index="'+index+'"]'))
                            // oDownLoad(imgSrc)
                            // oDownLoad(imgSrc)
                            // downloadFile('下载.png',imgSrc)
                        })



                    }
                }




                // 绑定显示操作栏事件
                var funBindHoverEvent = function () {
                    $(".upload_append_list").hover(
                        function (e) {
                            $(this).find(".file_bar").addClass("file_hover");
                        }, function (e) {
                            $(this).find(".file_bar").removeClass("file_hover");
                        }
                    );
                };


                if(isDefEl){
                    // 绑定删除按钮
                    funBindDelEvent();
                    funBindHoverEvent();


                    funBindSeeEvent(); // 查看文件
                    funBindDownEvent(); // 下载当前文件
                    uploadStyle()
                }else{

                    var params = {
                        fileInput: $("#fileImage").get(0),
                        uploadInput: $("#fileSubmit").get(0),
                        dragDrop: $("#fileDragArea").get(0),
                        url: $("#uploadForm").attr("action"),


                        filterFile: function (files) {
                            // 过滤合格的文件
                            return self.funFilterEligibleFile(files)
                        },
                        onSelect: function (selectFiles, allFiles) {

                            para.onSelect(selectFiles, allFiles);  // 回调方法
                            self.funSetStatusInfo(ZYFILE.funReturnNeedFiles());  // 显示统计信息
                            var html = '', i = 0;
                            // 组织预览html
                            var funDealtPreviewHtml = function () {
                                file = selectFiles[i];

                                if (file) {
                                    var reader = new FileReader()
                                    // 当读取操作成功完成时调用
                                    reader.onload = function (e) {
                                        // 处理下配置参数和格式的html
                                        html += self.funDisposePreviewHtml(file, e);

                                        i++;
                                        // 再接着调用此方法递归组成可以预览的html
                                        funDealtPreviewHtml();
                                    }
                                    reader.readAsDataURL(file); // 读出 base64

                                } else {
                                    // 走到这里说明文件html已经组织完毕，要把html添加到预览区
                                    if (html === ''){
                                        return
                                    }
                                    funAppendPreviewHtml(html);
                                }
                            };

                            // 添加预览html
                            var funAppendPreviewHtml = function (html) {
                                // 添加到添加按钮前
                                var el = ''
                                switch (para.upLoadName) {
                                    case 'fading':  // 法人身份证
                                        el = $('div[name="fading"]')
                                        break;
                                    case 'yingye':  // 营业执照
                                        el = $('div[name="yingye"]')
                                        break;
                                    case 'pdf-1': // pdf证明
                                        el = $('div[name="pdf-1"]')
                                        break;
                                    case 'pdf-2': // pdf证明
                                        el = $('div[name="pdf-2"]')
                                        break;
                                    case 'pdf-3': // pdf证明
                                        el = $('div[name="pdf-3"]')
                                        break;
                                    case 'pdf-4': // pdf证明
                                        el = $('div[name="pdf-4"]')
                                        break;

                                    default:
                                        break;
                                }

                                el.append(html).addClass('nothing');
                                el.find('.filePicker').hide()
                                $('#fileImage').val('')  // 清空上传
                                layer.closeAll()
                                // 添加有效日期
                                var a = $('.copyData').html()
                                for (var i = 0; i < selectFiles.length; i++) {
                                    $('#preview #uploadList_' + selectFiles[i].index + ' .uploadImg').append(a)
                                }

                                // 绑定删除按钮
                                funBindDelEvent();
                                funBindHoverEvent();


                                funBindSeeEvent(); // 查看文件
                                funBindDownEvent(); // 下载当前文件
                                uploadStyle()
                            };



                            funDealtPreviewHtml();
                        },
                        onDelete: function (file, files) {
                            // 移除效果
                            $("#uploadList_" + file.index).fadeOut().remove()
                            // 重新设置统计栏信息
                            self.funSetStatusInfo(files);
                            // console.info("剩下的文件");
                            // console.info(files);
                        },
                        onProgress: function (file, loaded, total) {
                        },
                        onSuccess: function (file, response) {
                            // 根据配置参数确定隐不隐藏上传成功的文件
                            if (para.finishDel) {
                                // 移除效果
                                $("#uploadList_" + file.index).fadeOut();
                                // 重新设置统计栏信息
                                self.funSetStatusInfo(ZYFILE.funReturnNeedFiles());
                            }
                        },
                        onFailure: function (file) {
                            layer.closeAll();
                            $("#uploadFailure_" + file.index).show();
                            // $("#uploadInf").append("<p>文件" + file.name + "上传失败！请选择其他文件上传</p>");
                        },
                        onComplete: function (response) {
                            layer.closeAll();
                            para.hasFileINBase  = true
                            $('#country2').val($("#country").val())
                            $('#province2').val($("#province").val())
                            $('#city2').val($("#city").val())
                            if($('.upload_append_list[hasbase="true"]').length > 0) self.setDataImgInfo()
                            else $('#btnSubmit').trigger("click");

                        },
                        onDragOver: function () {
                            $(this).addClass("upload_drag_hover");
                        },
                        onDragLeave: function () {
                            $(this).removeClass("upload_drag_hover");
                        }

                    };

                    ZYFILE = $.extend(ZYFILE, params);
                    ZYFILE.init();


                }
            };

            /**
             * 功能：绑定事件
             * 参数: 无
             * 返回: 无
             */
            this.addSetEvt = function(){

            }


            // 二次修改数据
            this.setDataImgInfo = function(){
                var arr = [], flag = true

                // 企业经营范围软体/软木 环评报告可选
                var val = $('#businessScope').val(),   // 0 木器  1 软体  2软木
                    html = $('#businessScope').find('option[value='+val+']').html()
                if((val === "1" && html === '软体') || (val === "2" && html === '软木')){
                    if($('.def_upload.important .upload_append_list').length < 3){
                        layer.msg('请上传的必传的供应商资料文件')
                        return false
                    }
                }else{
                    if($('.def_upload.important .upload_append_list').length < 4){
                        layer.msg('请上传的必传的供应商资料文件')
                        return false
                    }
                }


                $('.upload_append_list[hasbase="true"]').each(function(){
                    var _t = $(this)
                    if(_t.find('.dataEF').val() === ''){
                        layer.msg('请填写完整的开始时间');
                        flag = false
                        return false
                    }

                    var code = _t.attr('id').replace('uploadList_','')
                    var datEX = (_t.find('.dataEX').val() === ''? '' :  _t.find('.dataEX').val() + ' 00:00:00')
                    var obj = {
                        "qualificationCode": code,
                        "effectiveDate": _t.find('.dataEF').val() + ' 00:00:00',
                        "expireDate" : datEX
                    }
                    arr.push(obj)
                })




                // 更新
                function subajax_Qualifications(){
                    $.ajax({
                        url: para.baseUrl + '/supplier/supSupplier/updateQualifications',
                        type: 'post',
                        processData: false,
                        contentType: false,
                        data: JSON.stringify(arr),
                        dataType: 'json',
                        success: function (res) {
                            $('#country2').val($("#country").val())
                            $('#province2').val($("#province").val())
                            $('#city2').val($("#city").val())
                            para.hasFileINBase  = true
                            $('#btnSubmit').trigger("click");
                        },
                        error: function (jqXHR, textStatus, errorThrown) {
                            para.hasFileINBase = false
                            layer.msg('提交失败')
                        }
                    });
                }


                if(flag){
                    // 先删除图片再提交图片
                    if(para.delloadFile.length === 0){subajax_Qualifications()}
                    else{
                        $.ajax({
                            url: para.baseUrl + '/supplier/supSupplier/deleteFile',
                            type: 'post',
                            processData: false,
                            contentType: false,
                            data: JSON.stringify({
                                "qualificationCode": para.delloadFile.join(',')
                            }),
                            dataType: 'json',
                            success: function (res) {
                                subajax_Qualifications()
                            },
                            error: function (jqXHR, textStatus, errorThrown) {
                                layer.msg('删除失败,请重新删除')
                            }
                        });
                    }
                }
            }

            this.addEvent = function (t) {
                // 如果快捷添加文件按钮存在
                if ($(".filePicker").length > 0) {
                    // 绑定选择事件
                    $(".filePicker").bind("click", function (e) {  // 限定上传图片
                        var par = $(this).parent()
                        para.upLoadName = par.attr('name')
                        para.upLoadType = par.attr('type')
                        $("#fileImage").click();
                    });
                }

                // 绑定继续添加点击事件
                $(".webuploader_pick").bind("click", function (e) {
                    $("#fileImage").click()
                });



                // 绑定上传点击事件
                $(".upload_btn").bind("click", function (e) {
                    // 判断当前是否有文件需要上传
                    if (ZYFILE.funReturnNeedFiles().length > 0) {
                        var flag = true
                        $('.def_upload.important').off().each(function () {
                            if ($(this).find('.upload_append_list').length === 0){
                                flag = false;
                                return false
                            }
                        })

                        if (!flag) layer.msg('请上传的必传的供应商资料文件')
                        else{
                            // 为图片绑定数据上传
                            // 有效时间
                            var flag2 = true
                            $('.dataEF:visible').off().each(function(index,item){
                                var par = $(this).closest('.imgBox'),
                                    id = parseInt(par.attr('data-id')),
                                    _t = $(this)

                                if(_t.val() === ''){
                                    layer.msg('请填写完整的开始时间');
                                    flag2 = false
                                }
                                if(!flag2) return false;
                                for(var i=0; i<ZYFILE.uploadFile.length; i++){
                                    if(ZYFILE.uploadFile[i].index  === id){
                                        ZYFILE.uploadFile[i]['effectiveDate'] = _t.val()
                                    }
                                }
                            })



                            // 截止时间
                            $('.dataEX:visible').off().each(function(index,item){
                                var par = $(this).closest('.imgBox'),
                                    id = parseInt(par.attr('data-id')),
                                    _t = $(this)

                                // if(_t.val() === ''){
                                //     layer.msg('请填写完整的有效期时间'); 
                                //     flag2 = false
                                // } 
                                // if(!flag2) return false;
                                for(var i=0; i<ZYFILE.uploadFile.length; i++){
                                    if(ZYFILE.uploadFile[i].index  === id){
                                        ZYFILE.uploadFile[i]['expireDate'] = _t.val()
                                        ZYFILE.uploadFile[i]['typeName'] = par.closest('.def_upload').attr('tit')
                                        ZYFILE.uploadFile[i]['supSupplierCode'] = $('#supSupplierCode').val()
                                    }
                                }
                            })

                            if(flag2){
                                var index = layer.load(1, {
                                    shade: [0.7,'#000'] //0.1透明度的白色背景
                                });


                                // 先删除图片再提交图片
                                if(para.delloadFile.length === 0){$("#fileSubmit").click();}
                                else{
                                    $.ajax({
                                        url: para.baseUrl + '/supplier/supSupplier/deleteFile',
                                        type: 'post',
                                        processData: false,
                                        contentType: false,
                                        data: JSON.stringify({
                                            "qualificationCode": para.delloadFile.join(',')
                                        }),
                                        dataType: 'json',
                                        success: function (res) {
                                            $("#fileSubmit").click();
                                        },
                                        error: function (jqXHR, textStatus, errorThrown) {
                                            layer.msg('删除失败,请重新删除')
                                        }
                                    });
                                }

                            }
                        }
                    } else {
                        if($('.upload_append_list[hasbase="true"]').length > 0){
                            self.setDataImgInfo()
                        }
                        else if(para.hasFileINBase) self.setDataImgInfo()
                        else layer.msg('请上传有效的供应商资料文件')
                    }
                });



                // 如果快捷添加文件按钮存在
                if ($("#rapidAddImg").length > 0) {
                    // 绑定添加点击事件
                    $("#rapidAddImg").bind("click", function (e) {
                        $("#fileImage").click();
                    });
                }

            };


            // 初始化上传控制层插件
            this.init();
        });
    };
})(jQuery);

