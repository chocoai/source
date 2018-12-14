/*! WebUploader 0.1.5 替换被加密无法修改的文件 */
(function (b) {
	// 阿里云上传地址
	var alyData = {
		policy: '',
		OSSAccessKeyId: '',  // 密钥
		success_action_status: '',  // 状态
		signature: '',  // 签名
		key: '',
		callback: ''  // 回调
	}

	var getData = {
		url: '' // 阿里云地址
	}


	var webUpLoaderInfo = {
		addImg: [],
		delImg: [],
		needUpLoader: []  // 需要提交到阿里云的图片
	}

	


	// 获取签名
	b.ajax({
		url: 'https://am.uvanart.com/a/aliyunimage/amAliyunImage/getPolicy',
		// url: 'http://192.168.1.167:8080/a/aliyunimage/amAliyunImage/getPolicy',
		type: 'GET',
		contentType: 'text/plain;charset=UTF-8',
		dataType: 'json',
		success: function (res) {
			getData.url = res.data.host; 
			alyData.policy = res.data.policy;
			alyData.OSSAccessKeyId = res.data.accessId;
			alyData.signature = res.data.signature;
			alyData.key = res.data.dir;
			alyData.callback = res.data.callBack;
			alyData.success_action_status = res.code
		}
	})

	/* 
		b = $
		y = $(#Uploader)
		o = $(#Uploader #IDfileLists)
	*/
	var a = function (k, p) {
		var t = b.extend({}, b.fn.webuploader.defaults, k),
			x = t.id,
			l = "",
			w = t.uploadType == "image",
			y = b("#" + x + "Uploader"),
			o = y.find("#" + x + "fileLists");

		var s = function (M, O) {
			if (M) {
				t.i18n = t.i18n || {};
				var N = t.i18n[M];
				if (N && N != "") {
					if (O) {
						for (var K = 1; K < arguments.length; K++) {
							var L = new RegExp("\\{" + (K - 1) + "\\}", "gm");
							N = N.replace(L, arguments[K])
						}
					}
					return N
				}
			}
			return M
		};

		if (w) {
			o.appendTo(y.find(".queueList"))
		}

		var G = y.find(".statusBar"),
			e = G.find(".info"),
			d = y.find(".uploadBtn"),
			n = y.find(".placeholder"),
			v = G.find(".progress").hide(),
			H = 0,
			u = 0,
			q = window.devicePixelRatio || 1,
			r = 110 * q,
			c = 110 * q,
			m = "pedding",
			I = {},
			J = (function () {
				var K;
				try {
					K = navigator.plugins["Shockwave Flash"];
					K = K.description
				} catch (L) {
					try {
						K = new ActiveXObject("ShockwaveFlash.ShockwaveFlash").GetVariable("$version")
					} catch (M) {
						K = "0.0"
					}
				}
				K = K.match(/\d+/g);
				return parseFloat(K[0] + "." + K[1], 10)
			})(),
			E = (function () {
				var K = document.createElement("p").style,
					L = "transition" in K || "WebkitTransition" in K || "MozTransition" in K || "msTransition" in K || "OTransition" in K;
				K = null;
				return L
			})(),
			F = [],
			j = [],
			C = [],
			i = [],
			B = {
				bizType: t.bizType,
				bizKey: t.bizKey,
				uploadType: t.uploadType,
				imageMaxWidth: t.imageMaxWidth,
				imageMaxHeight: t.imageMaxHeight,
			};

		// 检查浏览器是否安装flash
		if (WebUploader.browser.ie && !WebUploader.Uploader.support("flash")) {
			if (J) {
				(function (K) {
					window.expressinstallcallback = function (N) {
						switch (N) {
							case "Download.Cancelled":
								alert(s("安装失败！"));
								break;
							case "Download.Failed":
								alert(s("安装失败！"));
								break;
							default:
								alert(s("安装已成功，请刷新！"));
								break
						}
						delete window.expressinstallcallback
					};
					var M = ctxStatic + "/webuploader/0.1/expressInstall.swf";
					var L = '<object type="application/x-shockwave-flash" data="' + M + '" ';
					if (WebUploader.browser.ie) {
						L += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" '
					}
					L += 'width="100%" height="100%" style="outline:0"><param name="movie" value="' + M + '" /><param name="wmode" value="transparent" /><param name="allowscriptaccess" value="always" /></object>';
					K.html(L)
				})(y)
			} else {
				y.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>')
			}
			return
		} else {
			if (!WebUploader.Uploader.support()) {
				js.showMessage(s("文件上传组件不支持您的浏览器，请使用高版本浏览器！"));
				return
			}
		}



		// k = 配置参数
		var k = {};
		if (!t.readonly) {
			k = b.extend({}, {
				pick: {
					id: "#" + x + "filePicker",
					label: s("点击选择文件")
				},
				dnd: "#" + x + "Uploader .queueList",
				paste: "#" + x + "Uploader .queueList"
			});

			// 通过当前限定上传文件内容显示按钮与规则
			if (t.uploadType == "image") {
				k.pick.label = s("点击选择图片");
				k.accept = {
					title: "Images",
					extensions: t.imageAllowSuffixes.replace(/\./g, ""),
					mimeTypes: "image/*"
				}
			} else {
				if (t.uploadType == "media") {
					k.pick.label = s("点击选择视频");
					k.accept = {
						title: "Medias",
						extensions: t.mediaAllowSuffixes.replace(/\./g, ""),
						mimeTypes: "*/*"
					}
				} else {
					if (t.uploadType == "file") {
						k.accept = {
							title: "Files",
							extensions: t.fileAllowSuffixes.replace(/\./g, ""),
							mimeTypes: "*/*"
						}
					} else {
						k.accept = {
							title: "All",
							extensions: (t.imageAllowSuffixes + t.mediaAllowSuffixes + t.fileAllowSuffixes).replace(/\./g, ""),
							mimeTypes: "*/*"
						}
					}
				}
			}
		}






		// 合并自定义属性 & 
		k = b.extend({}, {
			disableGlobalDnd: true,
			swf: ctxStatic + "/webuploader/0.1/Uploader.swf",
			// server: ctxAdmin + "/file/upload",
			server: getData.url,
			// formData: formData,
			threads: 1,
			fileNumLimit: t.maxUploadNum,
			fileSingleSizeLimit: t.maxFileSize,
			compress: false
		}, k);


		// 创建WebUploader
		var A = WebUploader.create(k);
		if (!window.webuploader) {
			window.webuploader = []
		}


		// 添加WebUploader并更新获取数据
		window.webuploader.push(A);
		if (!window.webuploaderRefresh) {
			window.webuploaderRefresh = function () {
				setTimeout(function () {
					for (var K in window.webuploader) {
						window.webuploader[K].refresh()
					}
				}, 200)
			}
		}



		// A = webuploader
		// 阻止此事件可以拒绝某些类型的文件拖入进来。目前只有 chrome 提供这样的 API，且只能通过 mime-type 验证。
		A.on("dndAccept", function (M) {  // M = 数据传输项 items
			var L = false,
				K = M.length,
				N = 0,
				O = "text/plain;application/javascript ";
			for (; N < K; N++) {
				if (~O.indexOf(M[N].type)) {
					L = true;
					break
				}
			}
			return !L
		});

		// 添加文件选择按钮
		A.addButton({
			id: "#" + x + "filePicker2",
			label: s("继续添加")
		});


		if (!t.isLazy) {
			d.hide()  // d = $('#Uploader .uploadBtn')
		}

		// 方法
		function D(M, K, N) {  // M = id;  K = 
			if (t.returnPath) {
				C.push(K);
				i.push(N);
				b("#" + t.filePathInputId).val(C.join("|")).change();
				b("#" + t.fileNameInputId).val(i.join("|")).change();
				try {
					b("#" + t.filePathInputId).valid()
				} catch (L) { }
				try {
					b("#" + t.fileNameInputId).valid()
				} catch (L) { }
			} else {
				F.push(M);
				b("#" + x).val(F.join(",")).change();  // x = t.id
				try {
					b("#" + x).valid()
				} catch (L) { }
			}
		}


		function h(M) {
			var N = M.attr("fileUploadId");
			if (N && N != null) {
				if (t.returnPath) {
					var K = M.attr("fileUrl");
					var O = M.attr("fileName");
					C.splice(b.inArray(K, C), 1);
					i.splice(b.inArray(O, i), 1);
					b("#" + t.filePathInputId).val(C.join("|"));
					b("#" + t.fileNameInputId).val(i.join("|"));
					try {
						b("#" + t.filePathInputId).valid()
					} catch (L) { }
					try {
						b("#" + t.fileNameInputId).valid()
					} catch (L) { }
				} else {
					
					F.splice(b.inArray(N, F), 1);
					j.push(N);

					b("#" + x).val(F.join(","));
					b("#" + x + "__del").val(j.join(","));
					// try {
					// 	b("#" + x).valid()
					// } catch (L) { }
					// try {
					// 	b("#" + x + "__del").valid()
					// } catch (L) { }
				}
			}
		}


		function z(M) {
			var L, K;
			if (M === m) {
				return
			}
			d.removeClass("state-" + m);
			d.addClass("state-" + M);
			m = M;
			switch (m) {
				case "pedding":
					n.removeClass("element-invisible");
					o.hide();
					G.addClass("element-invisible");
					break;
				case "ready":
					n.addClass("element-invisible");
					b("#" + x + "filePicker2").removeClass("element-invisible");
					o.show();
					G.removeClass("element-invisible");
					break;
				case "uploading":
					b("#" + x + "filePicker2").addClass("element-invisible");
					v.show();
					d.text(s("暂停上传"));
					break;
				case "paused":
					v.show();
					d.text(s("继续上传"));
					break;
				case "confirm":
					v.hide();
					b("#" + x + "filePicker2").removeClass("element-invisible");
					d.text(s("开始上传"));
					K = A.getStats();
					if (K.successNum && !K.uploadFailNum) {
						z("finish");
						return
					}
					break;
				case "finish":
					K = A.getStats();
					if (K.successNum) { } else {
						m = "done"
					}
					break
			}
			g()
		}

		// 进度?
		function f() {
			var K = 0,
				N = 0,
				L = v.children(),
				M;
			b.each(I, function (P, O) {
				N += O[0];
				K += O[0] * O[1]
			});
			M = N ? K / N : 0;
			L.eq(0).text(Math.round(M * 100) + "%");
			L.eq(1).css("width", Math.round(M * 100) + "%");
			g()
		}

		// 重新统计文件数与大小
		function g() {
			var L = "",
				K = A.getStats();
			if (m === "confirm" && K.uploadFailNum) {
				L = K.uploadFailNum + s(w ? "张图片" : "个文件") + s("上传失败") + ', <a class="retry" href="#">' + s("重新上传") + "</a>" + s("或") + '<a class="ignore" href="#">' + s("忽略") + "</a>"
			} else {
				if (m === "confirm" || m === "ready") {
					L = s("总共") + H + s(w ? "张图片" : "个文件") + (u <= 0 ? "" : "（" + WebUploader.formatSize(u) + "）")
				} else {
					L = s("已上传") + H + s(w ? "张图片" : "个文件") + (u <= 0 ? "" : "（" + WebUploader.formatSize(u) + "）");
					if (K.uploadFailNum) {
						L += ", " + s("失败{0}个", K.uploadFailNum)
					}
				}
			}
			e.html(L);
			if (H < t.maxUploadNum) {
				b("#" + x + "filePicker2").show()
			} else {
				b("#" + x + "filePicker2").hide()
			}
			
			window.webuploaderRefresh()
		}


		// 监听
		//上传过程中触发，携带上传进度
		A.onUploadProgress = function (L, K) {   // L{Object} = File对象;  K{Object} = 服务端的返回数据，json格式，如果服务端不是json格式，从ret._raw中取数据，自行解析
			var M;
			if (w) {
				M = b("#" + x + L.id),
					$percent = M.find(".progress span");

				$percent.css("width", K * 100 + "%")
			} else {
				M = b("#" + x + L.id).find(".prog_bar"),
					$percent = M.find(".progress-bar");

				$percent.css("width", Math.round(K * 100) + "%");
				$percent.text(Math.round(K * 100) + "%");
			}

			I[L.id][1] = K;
			f()
		};



		// 当文件被加入队列以后触发
		A.onFileQueued = function (K) {  // K{File} = File对象
			if (H >= t.maxUploadNum) {
				js.showMessage(s("您只能上传{0}个文件", t.maxUploadNum));
				return
			}
			js.loading(s("正在验证文件，请稍等。"));
			d.addClass("disabled");


			// 计算文件 md5 值，返回一个 promise 对象，可以监听 progress 进度
			A.md5File(K, 0, 10 * 1024 * 1024).then(function (R) {
				H++;
				u += K.size;
				// var P_file = {}  // 临时存储base64
				

				if (H === 1 && !t.readonly) {
					G.show()
				}


				if (w) {
					var S = b('<li id="' + x + K.id + '" sad="csss"><input id="' + x + K.id + '_md5" type="hidden"/><p id="' + x + K.id + '_name" class="title">' + K.name + '</p><p class="imgWrap"></p><p class="progress"><span></span></p></li>'),
						O = b('<div class="file-panel"><span class="cancel" fileuploadid="' + x + K.id + '" fileurl="" filename="' + K.name + '" filesize="' + K.size + '">' + s("删除") + "</span></div>").appendTo(S),
						N = S.find("p.progress-bar"),
						M = S.find("p.imgWrap"),
						Q = b('<p class="error"></p>'),

						L = function (T) {
							var U = "";
							switch (T) {
								case "exceed_size":
									U = s("文件大小超出");
									break;
								case "interrupt":
									U = s("文件传输中断");
									break;
								case "http":
									U = s("HTTP请求错误");
									break;
								case "not_allow_type":
									U = s("文件格式不允许");
									break;
								default:
									U = s("上传失败，请重试");
									break
							}
							if (U != null) {
								Q.text(U).appendTo(S)
							}
						};


					if (K.getStatus() === "invalid") {
						L(K.statusText)
					} else {
						M.text(s("预览中"));
						A.makeThumb(K, function (U, V) {
							if (U) {
								M.text(s("不能预览"));
								return
							}
							var T = b('<img src="' + V + '">');
							
							M.empty().append(T)
						}, r, c);
						I[K.id] = [K.size, 0];
						K.rotation = 0
					}


					
					



					// 文件状态变化
					K.on("statuschange", function (U, T) {
						if (T === "progress") {
							N.hide().width(0)
						}
						if (U === "error" || U === "invalid") {
							L(K.statusText);
							I[K.id][1] = 1
						} else {
							if (U === "interrupt") {
								L("interrupt")
							} else {
								if (U === "queued") {
									I[K.id][1] = 0
								} else {
									if (U === "progress") {
										Q.remove();
										N.css("display", "block")
									}
								}
							}
						}
						S.removeClass("state-" + T).addClass("state-" + U)
					});

					/*
						S = 当前新增文件所在li
					*/

					//移动上去
					S.on("mouseenter", function () {
						O.stop().animate({
							height: 30
						})
					});
					// 离开
					S.on("mouseleave", function () {
						O.stop().animate({
							height: 0
						})
					});

					// 点击删除图片
					O.on("click", "span", function () {
						var V = b(this);
						switch (V.index()) {
							case 0:
								var T = b(this);
								js.confirm(s("确定删除该图片吗？"), function (W) {
									h(T);
									A.removeFile(K)
								});
								return;
							case 1:
								K.rotation += 90;
								break;
							case 2:
								K.rotation -= 90;
								break
						}
						if (E) {
							var U = "rotate(" + K.rotation + "deg)";
							M.css({
								"-webkit-transform": U,
								"-mos-transform": U,
								"-o-transform": U,
								transform: U
							})
						} else {
							M.css("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + (~~((K.rotation / 90) % 4 + 4) % 4) + ")")
						}
					})
				} else {
					var S = b('<tr id="' + x + K.id + '" filePath="'+dnum + K.name+'" class="template-upload"><input id="' + x + K.id + '_md5" type="hidden" value="' + dnum + K.name + '"/><td id="' + x + K.id + '_name" class="name">' + K.name + '</td><td class="size">' + WebUploader.formatSize(K.size) + '</td><td class="prog_bar"><p class="progress"><span class="progress-bar">0%</span></p></td></tr>'),
						N = S.find(".progress-bar"),
						Q = b('<td class="msg"></td>'),
						L = function (T) {
							var U = "";
							switch (T) {
								case "exceed_size":
									U = "<span class='label label-sm label-danger'>" + s("文件大小超出") + "</span>";
									break;
								case "interrupt":
									U = "<span class='label label-sm label-danger'>" + s("文件传输中断") + "</span>";
									break;
								case "http":
									U = "<span class='label label-sm label-danger'>" + s("HTTP请求错误") + "</span>";
									break;
								case "not_allow_type":
									U = "<span class='label label-sm label-danger'>" + s("文件格式不允许") + "</span>";
									break;
								default:
									U = "<span class='label label-sm label-danger'>" + s("上传失败，请重试") + "</span>";
									break
							}
							if (U != null) {
								Q.html(U)
							}
						};
					if (K.getStatus() === "invalid") {
						L(K.statusText);
						Q.appendTo(S)
					} else {
						I[K.id] = [K.size, 0];
						K.rotation = 0;
						Q.text(s("等待上传")).appendTo(S)
					}
					K.on("statuschange", function (U, T) {
						if (T === "progress") { }
						if (U === "error" || U === "invalid") {
							L(K.statusText);
							I[K.id][1] = 1;
							N.text("0%").css("width", "0%")
						} else {
							if (U === "interrupt") {
								L("interrupt");
								N.text("0%").css("width", "0%")
							} else {
								if (U === "progress") {
									Q.text(s("正在上传"));
									N.css("display", "block")
								} else {
									if (U === "complete") { }
								}
							}
						}
						S.removeClass("state-" + T).addClass("state-" + U)
					});
					O = b('<td class="btncancel"><a class="btn btn-default btn-xs yellow"><i class="fa fa-ban"></i> ' + s("取消") + " </a></td>").appendTo(S);
					O.on("click", "a", function () {
						var U = b(this);
						switch (U.index()) {
							case 0:
								A.removeFile(K);
								return;
							case 1:
								var T = b(this);
								js.confirm(s("确定删除该文件吗？"), function (V) {
									h(T);
									A.removeFile(K)
								});
								return
						}
					})
				}

				S.appendTo(o);
				z("ready");
				f();
				
				// b.extend()
				
				// 仅作显示操作,与上传数据无关
				var P = null;
				P = {
					fileMakeThumb: K,  // 用来展示临时显示图片64base
					createBy: "system",
					createByName: "超级管理员",
					createDate: '',
					fileEntity: {
						fileContentType: K.type,
						fileExtension: K.ext,
						fileId: x+K.id,
						fileMd5: R,
						filePath: '',
						fileRealPath: '',
						fileSize: K.size,
						fileSizeFormat: (K.size / (1024 * 1024)).toFixed(2) + 'MB',
						fileUrl: '',
						id: x+K.id ,
						status: 0
					},
					fileName: K.name,
					fileType: 'file',
					id: x+K.id ,
					status: 0,
					updateBy: 'system',
					updateByName: '超级管理员',
					updateDate: ''
				}
				

				if (P) {
					if (w) {
						P.message = '<p class="error" title="图片添加成功">图片添加成功</p>'
					} else {
						P.progress = '<p class="progress"><span class="progress-bar" style="display:block;width:100%;">100%</span></p>';
						P.message = '<span class="label label-sm label-success" title="图片添加成功">图片添加成功</span>'
					}

					A.removeFile(K);
					p.refreshFileList([P], false)
				}

				d.removeClass("disabled");
				if (!t.isLazy) {

					d.click()
				}
				js.closeLoading()

				webUpLoaderInfo.needUpLoader.push({
					id: x+K.id,
					file: K,
					md5: R,
					bizKey: t.bizKey,
					bizType: t.bizType,
					w: w,
					A: A,
					p: p
				})
			})
		};

		/* 
			当某个文件上传到服务端响应后，会派送此事件来询问服务端响应是否有效。
			如果此事件handler返回值为false, 则此文件将派送server类型的uploadError事件。

			Q{Object} = object; 
			K{Object} = 服务端的返回数据，json格式，如果服务端不是json格式，从ret._raw中取数据，自行解析
		*/
		A.on("uploadAccept", function (Q, K, O) {
			var N = (K._raw || K);
			var L = JSON.parse(N);

			if (L.result == "false") {
				O(L.code)
			}
			var R = b("#" + x + Q.file.id);
			try {
				var N = (K._raw || K),
					L = JSON.parse(N);
				if (w) {
					b('<p class="error" title="' + L.message + '">' + L.message + "</p>").appendTo(R)
				} else {
					var M = (L.result == "true") ? "success" : "danger";
					R.find(".msg").html('<span class="label label-sm label-' + M + '" title="' + L.message + '">' + L.message + "</span>")
				}
			} catch (P) {
				if (w) {
					b('<p class="error">' + s("服务器返回出错") + "</p>").appendTo(R)
				} else {
					R.find(".msg").html('<span class="label label-sm label-danger">' + s("服务器返回出错") + "</span>")
				}
			}
		});



		/*
			event: 当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次
			在发送之间可以添加字段什么的。。。
			如果默认的字段不够使用，可以通过监听此事件来扩展

			K{Object} = object; 
			L{Object} = data默认的上传参数，可以扩展此对象来控制上传参数; 
			M{Object} = headers可以扩展此对象来控制上传头部 
		*/
		A.on("uploadBeforeSend", function (K, L, M) {
			M.X_Requested_With = "XMLHttpRequest";
			L.fileMd5 = b("#" + x + L.id + "_md5").val();
			L.fileName = b("#" + x + L.id + "_name").text()
		});

		// 当文件上传成功时触发
		A.on("uploadSuccess", function (L, Q) { // L{File} = File对象;  Q{Object} = response服务端返回的数据
			var K = b("#" + x + L.id).find(".btncancel");
			var O = b("#" + x + L.id).find(".file-panel");
			var S = b("#" + x + L.id).find(".progress-bar");
			try {
				var R = (Q._raw || Q),
					N = JSON.parse(R);
				if (N.result == "true") {
					var T = N.fileUpload,
						U = (js.startWith(T.fileEntity.fileUrl, ctxPath) ? "" : ctxPath) + T.fileEntity.fileUrl,
						M = (T.id == "" ? U : ctxAdmin + "/file/download/" + T.id);
					if (w) {
						O.find(".cancel").attr("fileUploadId", T.id).attr("fileUrl", U).attr("fileName", T.fileName).attr("fileSize", T.fileEntity.fileSize)
					} else {
						K.find("a").hide();
						b('<a class="btn btn-danger btn-xs" fileUploadId="' + T.id + '" fileUrl="' + U + '" fileName="' + T.fileEntity.fileName + '" fileSize="' + T.fileEntity.fileSize + '"><i class="fa fa-trash-o"></i> ' + s("删除") + '</a> &nbsp;<a class="btn btn-info btn-xs" target="_blank" href="' + ctxAdmin + "/file/download/" + T.id + '" ><i class="fa fa-download"></i> ' + s("下载") + "</a>").appendTo(K)
					}
					D(T.id, U, T.fileName)
				} else {
					S.css("width", "0%").text("0%")
				}
			} catch (P) {
				S.css("width", "0%").text("0%");
				error(P)
			}
			g()
		});


		// 当文件被移除队列后触发
		A.onFileDequeued = function (K) {  // K{File} = File对象
			H--;
			if (!H) {
				// 提示红字显示需要上传图片
				z("pedding")
			}
			u -= K.size;
			delete I[K.id];
			f();
			if (w) {
				b("#" + x + K.id).off().find(".file-panel").off().end().remove()
			} else {
				b("#" + x + K.id).remove()
			}
		};

		// 
		A.on("all", function (L, M) {
			var K;
			switch (L) {
				case "uploadFinished":
					z("confirm");
					break;
				case "startUpload":
					z("uploading");
					break;
				case "stopUpload":
					z("paused");
					break
			}
		});

		/*
			当validate不通过时，会以派送错误事件的形式通知调用者

			Q_EXCEED_NUM_LIMIT 在设置了fileNumLimit且尝试给uploader添加的文件数量超出这个值时派送。
			Q_EXCEED_SIZE_LIMIT 在设置了Q_EXCEED_SIZE_LIMIT且尝试给uploader添加的文件总大小超出这个值时派送。
			Q_TYPE_DENIED 当文件类型不满足时触发。。

		*/
		A.onError = function (K) {
			var L = "";
			switch (K) {
				case "Q_TYPE_DENIED":
					L = s("文件类型不对");
					break;
				case "F_EXCEED_SIZE":
					L = s("文件大小超出");
					break;
				case "F_DUPLICATE":
					L = s("不要选择重复文件");
					break;
				case "Q_EXCEED_NUM_LIMIT":
					L = s("您只能上传{0}个文件", t.maxUploadNum);
					break;
				case "Q_EXCEED_SIZE_LIMIT":
					L = s("文件大小超出");
					break;
				default:
					L = s("上传失败，请重试");
					break
			}
			js.showMessage(L)
		};


		d.on("click", function () {
			if (b(this).hasClass("disabled")) {
				return false
			}
			if (m === "ready") {
				A.upload()
			} else {
				if (m === "paused") {
					A.upload()
				} else {
					if (m === "uploading") {
						A.stop()
					}
				}
			}
		});

		e.on("click", ".retry", function () {
			A.retry();
			return false
		});

		e.on("click", ".ignore", function () {
			var L, K, M = A.getFiles("error");
			for (K = 0; L = M[K++];) {
				A.removeFile(L)
			}
			z("finish");
			f();
			return false
		});

		d.addClass("state-" + m);
		f();

		p.refreshFileList = function (O, L) {  // 新增L false  已有true
			if (L) {
				o.empty();
				H = 0;
				u = 0;
				F = [];
				C = [];
				i = []
			}
			if (O && O.length > 0) {
				for (var N = 0; N < O.length; N++) {
					var M = O[N],
						K = (js.startWith(M.fileEntity.fileUrl, ctxPath) ? "" : ctxPath) + M.fileEntity.fileUrl,
						P = M.fileEntity.fileRealPath;


					
					
					if (w) {
						function addEvent(){
							$li.on("click", ".imgWrap img", function () {
								var R = b(this),
									T = R.attr("src"),
									U = "#outerdiv",
									S = "#innerdiv",
									Q = "#bigimg";
								if (b(U).length == 0) {
									b('<div id="outerdiv" style="position:fixed;top:0;left:0;background:rgba(0,0,0,0.7);z-index:99999;width:100%;height:100%;display:none;"><div id="innerdiv" style="position:fixed;"><img id="bigimg" style="border:2px solid #fff;" src="" /></div></div>').appendTo(document.body)
								}
								b(Q).attr("src", T);
	
	
								b("<img/>").attr("src", T).load(function () {
									var V = b(window).width();
									var Z = b(window).height();
									var ac = this.width;
									var Y = this.height;
									var aa, ab, W = 0.8;
									if (Y > Z * W) {
										ab = Z * W;
										aa = ab / Y * ac;
										if (aa > V * W) {
											aa = V * W
										}
									} else {
										if (ac > V * W) {
											aa = V * W;
											ab = aa / ac * Y
										} else {
											aa = ac;
											ab = Y
										}
									}
									b(Q).css("width", aa);
									var ad = (V - aa) / 2;
									var X = (Z - ab) / 2;
									b(S).css({
										top: X,
										left: ad
									});
									b(U).fadeIn("fast")
								});
								b(U).click(function () {
									b(this).fadeOut("fast")
								})
							});
							$li.on("mouseenter", function () {
								var Q = b(this).index();
								o.find(".file-panel").eq(Q).stop().animate({
									height: 30
								})
							});
							$li.on("mouseleave", function () {
								var Q = b(this).index();
								o.find(".file-panel").eq(Q).stop().animate({
									height: 0
								})
							});
							$li.on("click", "span", function () {
								var U = b(this),
									V = b(this).closest("li"),
									R = b(this).parent().data("fileRotation");
								if (!R) {
									R = 0
								}
								switch (U.index()) {
									case 0:
										if (!t.readonly) {
											var U = b(this);
											js.confirm(s("确定删除该图片吗？"), function (X) {
												h(U);
												if (t.returnPath) {
													A.onFileDequeued({
														id: 0,
														size: 0
													})
												} else {
													var W = U.attr("fileSize");
													A.onFileDequeued({
														id: 0,
														size: W
													})
												}
												
												// 若当前addImg已有则先删除
												var list = webUpLoaderInfo.needUpLoader
												for(var i in list){
													if(list[i].id === V.attr('id')){
														list.splice(i,1)
														break;
													}
												}
	
												if(V.attr('filepath')){
													webUpLoaderInfo.delImg.push({
														id: V.attr('id'),
														filePath: V.attr('filepath')
													})
												}
												
												V.remove()
											})
										}
										return;
									case 1:
										R += 90;
										break;
									case 2:
										R -= 90;
										break
								}
								var Q = b(this).parent().parent().index();
								var T = o.find(".imgWrap :eq(" + Q + ")");
								if (E) {
									var S = "rotate(" + R + "deg)";
									T.css({
										"-webkit-transform": S,
										"-mos-transform": S,
										"-o-transform": S,
										transform: S
									})
								} else {
									T.css("filter", "progid:DXImageTransform.Microsoft.BasicImage(rotation=" + (~~((R / 90) % 4 + 4) % 4) + ")")
								}
								b(this).parent().data("fileRotation", R)
							})
						}
						

						if(!L){  // 新增图片
							A.makeThumb(M.fileMakeThumb, function( error, ret ) {
								if ( error ) {
									$li.text('预览错误');
								} else {
									$li = b('<li id="' + M.id + '" filePath="'+M.fileEntity.filePath+'"><p class="title"><a target="_blank" href="' + ret + '">' + M.fileName + '</a></p><p class="imgWrap"><img src="' + ret  + '"/></p><p class="progress"><span></span></p><div class="file-panel"><span class="cancel ' + (!t.readonly ? "" : "hide") + '" fileUploadId="' + M.id + '" fileUrl="' + K + '" fileName="' + M.fileName + '" fileSize="' + M.fileEntity.fileSize + '">' + s("删除") + "</span></div>" + (M.message ? M.message : "") + "</li>")
									addEvent()
									H++;
									u += M.fileEntity.fileSize;
									o.append($li).show();
									D(M.id, K, M.fileName)
									z("ready")
								}
							});
						}else{
							$li = b('<li id="' + M.id + '" filePath="'+M.fileEntity.filePath+'"><p class="title"><a target="_blank" href="' + P + '">' + M.fileName + '</a></p><p class="imgWrap"><img src="' + P + '"/></p><p class="progress"><span></span></p><div class="file-panel"><span class="cancel ' + (!t.readonly ? "" : "hide") + '" fileUploadId="' + M.id + '" fileUrl="' + K + '" fileName="' + M.fileName + '" fileSize="' + M.fileEntity.fileSize + '">' + s("删除") + "</span></div>" + (M.message ? M.message : "") + "</li>")
							addEvent()
							H++;
							u += M.fileEntity.fileSize;
							o.append($li);
							D(M.id, K, M.fileName)
						}
						
					} else {
						$li = b('<tr id="' + M.id + '"  class="template-upload"><td class="name">' + M.fileName + '</td><td class="size">' + (t.returnPath ? "" : WebUploader.formatSize(M.fileEntity.fileSize)) + '</td><td class="prog_bar">' + (M.progress ? M.progress : (M.createByName ? M.createByName : "")) + '</td><td class="msg">' + (M.message ? M.message : (M.createDate ? M.createDate : "")) + '</td><td class="btncancel">' + (t.preview != "" ? '<a class="btn btn-default btn-xs preview" herf="javascript:void(0);" previewUrl="' + K + "?fileName=" + M.fileName + "&preview=" + t.preview + '"><i class="fa fa-eye"></i> ' + s("预览") + "</a> &nbsp;" : "") + '<a class="btn btn-danger btn-xs delete' + (!t.readonly ? "" : "hide") + '" fileUploadId="' + M.id + '" fileUrl="' + K + '" fileName="' + M.fileName + '" fileSize="' + M.fileEntity.fileSize + '"><i class="fa fa-trash-o"></i> ' + s("删除") + ' </a> &nbsp;<a class="btn btn-info btn-xs blue" target="_blank" href="' + P + '"><i class="fa fa-download"></i> ' + s("下载") + " </a></td></tr>");
						$li.on("click", "a.preview", function () {
							js.addTabPage(b(this), s("查看"), b(this).attr("previewUrl"), true, false)
						});
						if (!t.readonly) {
							$li.on("click", "a.delete", function () {
								var R = b(this),
									Q = b(this).closest("tr");
								js.confirm(s("确定删除该文件吗？"), function (T) {
									h(R);
									if (t.returnPath) {
										A.onFileDequeued({
											id: 0,
											size: 0
										})
									} else {
										var S = R.attr("fileSize");
										A.onFileDequeued({
											id: 0,
											size: 0
										})
									}
									Q.remove()
								});
								return
							})
						}

						H++;
						u += M.fileEntity.fileSize;
						o.append($li);
						D(M.id, K, M.fileName)
					}
					
				}
			}
			if (F.length > 0 || C.length > 0) {
				if (!t.readonly) {
					G.show()
				}
				z("ready")

			}
			f()
		};


		p.refreshFileListByPath = function () {
			var M = [],
				O = [],
				N, K = b("#" + t.filePathInputId).val(),
				L = b("#" + t.fileNameInputId).val();
			if (K != undefined && K != "") {
				O = K.split("|")
			}
			if (L != undefined && L != "") {
				N = L.split("|")
			}
			if (N == undefined || N.length != O.length) {
				N = O
			}


			b.each(O, function (P, S) {
				var R = N[P].split("/");
				var Q = R[R.length - 1];
				M.push({
					id: Q.split(".")[0],
					fileName: Q,
					fileEntity: {
						fileUrl: S,
						fileSize: 0
					},
				})
			});
			p.refreshFileList(M, true)
		};

		// 第一次进入页面拿数据
		p.refreshFileListByBizData = function () {
			b.ajax({
				url: ctxAdmin+'/file/query?bizKey=' + t.bizKey + '&bizType=' + t.bizType,
				type: 'get',
				contentType: 'text/plain;charset=UTF-8',
				dataType: "json",
				success: function (K) {
					// console.log('拿数据', K)
					if (!(K.result == "false")) {
						p.refreshFileList(K, true)
					}
				}
			})
		};

		if (t.returnPath) {
			p.refreshFileListByPath()
		} else {
			p.refreshFileListByBizData()
		}

		return p
	};


	// 在jq添加pro 文件上传方法
	b.fn.webuploader = function (d, f) {
		var e;
		var c = this.each(function () {
			var i = b(this);
			var h = i.data("webuploader");
			var g = typeof d === "object" && d;
			if (!h) {
				i.data("webuploader", (h = new a(g, i)))
			}
			if (typeof d === "string" && typeof h[d] === "function") {
				if (f instanceof Array) {
					e = h[d].apply(h, f)
				} else {
					e = h[d](f)
				}
			}
		});
		return (e === undefined) ? c : e
	};



	// 默认配置
	b.fn.webuploader.defaults = {
		id: "",
		bizKey: "",
		bizType: "",
		readonly: false,
		returnPath: false,
		filePathInputId: "",
		fileNameInputId: "",
		uploadType: "",
		imageAllowSuffixes: ".gif,.bmp,.jpeg,.jpg,.ico,.png,.tif,.tiff,",
		mediaAllowSuffixes: ".flv,.swf,.mkv,webm,.mid,.mov,.mp3,.mp4,.m4v,.mpc,.mpeg,.mpg,.swf,.wav,.wma,.wmv,.avi,.rm,.rmi,.rmvb,.aiff,.asf,.ogg,.ogv,",
		fileAllowSuffixes: ".doc,.docx,.rtf,.xls,.xlsx,.csv,.ppt,.pptx,.pdf,.vsd,.txt,.md,.xml,.rar,.zip,7z,.tar,.tgz,.jar,.gz,.gzip,.bz2,.cab,.iso,",
		maxFileSize: 100 * 1024 * 1024,
		maxUploadNum: 300,
		imageMaxWidth: 1024,
		imageMaxHeight: 768,
		isLazy: false,
		preview: ""
	};


	// 上传阿里云
	b.fn.webUpLoaderFun = function (callBack) {
		//加载
		layer.load(1, {
			title: '上传中...',
			shade: [0.1,'#000'] //0.1透明度的白色背景
		});

		var P = null;
			code = 400
		
			
		subAjax()
		function subAjax(){
			if(webUpLoaderInfo.needUpLoader.length === 0){
				b.ajax({
					url: ctxAdmin+'/file/saveFile',
					type: 'post',
					data: JSON.stringify({
						addImg: webUpLoaderInfo.addImg,
						delImg: webUpLoaderInfo.delImg
					}),
					contentType: 'text/plain;charset=UTF-8',
					dataType: 'json',
					success: function (res) {
						if(res.code === "200"){
							// 清空原型中数据
							webUpLoaderInfo.addImg = []
							webUpLoaderInfo.delImg = []
							code = 200
						}else{
							layer.msg('保存失败,请重新保存')
							code = 400
						}
						callBack(code)
						return
					},
					error: function(){
						code = 400
						callBack(code)
					}
				})

				return
			}

			

			// 为上传到阿里云图片添加唯一标识区分
			var dataDay = new Date(),
				K = webUpLoaderInfo.needUpLoader[0].file,  // 需要上传的队列
			dnum = dataDay.getFullYear().toString() + dataDay.getMonth().toString() + dataDay.getDate().toString() + dataDay.getMinutes().toString() + dataDay.getMilliseconds().toString() + dataDay.getSeconds().toString();

			// 提交到阿里云的图片
			var formData = new FormData;
				formData.append('OSSAccessKeyId', alyData.OSSAccessKeyId)
				formData.append('policy', alyData.policy)
				formData.append('Signature', alyData.signature)
				formData.append('key', alyData.key + dnum + "${filename}")
				formData.append('success_action_status', alyData.success_action_status)
				formData.append('callback', alyData.callback)
				formData.append('file', K.source.source)


			b.ajax({
				type: "POST",
				url: getData.url,   // 阿里云上传地址
				data: formData,
				processData: false,
				cache: false,
				async: false,
				contentType: false,
				timeout: 10000,
				dataType: "json",
				header: {
					'content-type': "multipart/form-data"
				},
				success: function (T) {
					if (T.code === 200) {
						var d = new Date(),
							fileName = T.data.split('/');  // 文件名
						

							
						var dataTime = {
							mm: (parseInt(d.getMonth() + 1)>=10 ? parseInt(d.getMonth() + 1) : '0'+parseInt(d.getMonth() + 1)),
							dd: (d.getDate().toString().length===2 ? d.getDate() : '0'+d.getDate()),
							hh: (d.getHours().toString().length===2 ? d.getHours() : '0'+d.getHours()),
							mu: (d.getMinutes().toString().length===2 ? d.getMinutes() : '0'+d.getMinutes())
						}

						
						// 手动添加数据
						var obj = {
							bizKey: webUpLoaderInfo.needUpLoader[0].bizKey,
							bizType: webUpLoaderInfo.needUpLoader[0].bizType,
							createBy: "system",
							createByName: "超级管理员",
							createDate: d.getFullYear() + '-' + dataTime.mm + '-' + dataTime.dd + ' ' + dataTime.hh + ':' + dataTime.mu,
							fileEntity: {
								fileContentType: K.type,
								fileExtension: K.ext,
								fileId: fileName[fileName.length - 1],
								fileMd5: webUpLoaderInfo.needUpLoader[0].md5,
								filePath: T.data,
								fileRealPath: getData.url + T.data,
								fileSize: K.size,
								fileSizeFormat: (K.size / (1024 * 1024)).toFixed(2) + 'MB',
								fileUrl: getData.url + T.data,
								id: T.id,
								status: 0
							},
							fileName: K.name,
							fileType: 'file',
							id: T.id,
							status: 0,
							updateBy: 'system',
							updateByName: '超级管理员',
							updateDate: d.getFullYear() + '-' +  dataTime.mm + '-' + dataTime.dd + ' ' + dataTime.hh + ':' + dataTime.mu
						}

						
						P = obj;


						
						if (webUpLoaderInfo.needUpLoader[0].w) {
							P.message = '<p class="error" title="' + T.msg + '">' + T.msg + "</p>"
						} else {
							P.progress = '<p class="progress"><span class="progress-bar" style="display:block;width:100%;">100%</span></p>';
							P.message = '<span class="label label-sm label-success" title="' + T.msg + '">' + T.msg + "</span>"
						}

						
						webUpLoaderInfo.addImg.push(P)
						webUpLoaderInfo.needUpLoader.splice(0,1)
						subAjax()
						
						
						if (P) {
							if(!webUpLoaderInfo.needUpLoader[0]) return
							webUpLoaderInfo.needUpLoader[0].A.removeFile(K);
							webUpLoaderInfo.needUpLoader[0].p.refreshFileList([P], false)
						}	
					}
				}
			});
		}


		
		
	}


	// 语言包
	if (window.lang == "en") {
		b.fn.webuploader.defaults.i18n = {
			"安装失败！": "Installation failed!",
			"安装已成功，请刷新！": "Installation successful, please refresh!",
			"文件上传组件不支持您的浏览器，请使用高版本浏览器！": "Does not support your browser, please use the high version browser!",
			"点击选择文件": "Select files",
			"点击选择图片": "Select images",
			"点击选择视频": "Select videos",
			"继续添加": "Add files",
			"暂停上传": "Pause upload",
			"继续上传": "Continue upload",
			"开始上传": "Start upload",
			"上传成功": "Upload success",
			"张图片": " images",
			"个文件": " files",
			"上传失败": " Failure",
			"重新上传": " Retry ",
			"或": " or ",
			"忽略": " Ignore ",
			"总共": "Total ",
			"已上传": "Uploaded ",
			"失败{0}个": " {0} failure",
			"您只能上传{0}个文件": "You can only upload {0} files.",
			"正在验证文件，请稍等。": "File is being validated.",
			"删除": "Delete",
			"向右旋转": "Rotate right",
			"向左旋转": "Rotate left",
			"文件类型不对": "File type error",
			"文件大小超出": "File size exceeded",
			"文件传输中断": "File transfer interrupt",
			"HTTP请求错误": "HTTP request error",
			"文件格式不允许": "File format not allowed",
			"不要选择重复文件": "Do not select duplicate files",
			"上传失败，请重试": "Upload failed, please try again",
			"服务器返回出错": "The server returned an error",
			"预览中": "In the preview",
			"不能预览": "Cannot preview",
			"确定删除该图片吗？": "Are you sure to delete the image?",
			"确定删除该文件吗？": "Are you sure to delete this file?",
			"等待上传": "Waiting for the upload",
			"正在上传": "Are uploading",
			"取消": "Cancel",
			"下载": "Download",
			"预览": "Preview",
			"查看": "To view"
		}
	}
})(jQuery);