// JavaScript Document
var PATH_API = '../ReceitaCertaBackend/ViewController';
var NOVOACTION = 'novo';
var SALVARACTION = 'salvar';
var EDITACTION = 'edit';
var DIVHIDDENS = 'camposHidden'; // DIV PADRÃO PARA CAMPOS HIDDENS
var MODALMSG = 'modalMsg';
var CORPOMODALMSG = 'corpoModalMsg';
var KEYDADOS = 'dados';
var KEYCAMPO = 'campo';
var KEYBUSCA = 'busca';
var KEYTITULOMODAL = 'Sistema';
var MODALTITULO = 'tituloModalGeral';
var KEYCONFIG = 'config';
var MAPNAV = {"Login":{"url":"acess.html","central":false},"Crud":{"url":"index.html","central":false}}; // Mapa de navegação
var BTNMODALMSG = "btnModalMsg";
var CONFIGTABLE = "";
var MODALCONFIRM = "confirmModal";
var CORPOMODALCONFIRM = "corpoConfirmModal";
var OBJGERAL = "";
var KEYFORM = "dataType";
var MODEL = "MODEL";
var TIPOCAMPO = "fieldClassification";
var CLASSNAMECAMPO = "objectClass";
var DATAGERAL = "data";
//	objAction = {'action':EDITACTION,'className':classe,'id':sessionStorage.id}; <- action, className, useCase, id

// Uso de prototypes

// Prototipo para colocar o primeiro caracter de uma string em Maiusculo
String.prototype.capitalizeFirstLetter = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}


function serializaVetor(obj,qtdCampos){
	console.log(obj);
	var novoArray = new Array();
	var j = 0;
	var cont = 0;
	novoArray[0] = new Object();
	$.each(obj,function(i, valor){
		if(j == qtdCampos && qtdCampos != ''){
			j = 0;
			cont++;
			novoArray[cont] = new Object();
		}
		novoArray[cont][valor.name] = valor.value;
		j++;
	});
	console.log(novoArray);
	
	return novoArray;
}


function construirForm(dados,nomeForm,resetForm){ // Construção dinamica de um formulario qualquer
  console.log(dados);
  var data = dados.data;
	console.log(data);
		var html = '';
		var selectedOption = '';
		var metodoGeral = '';
		$.each(data[KEYFORM],function(campo,values){
			console.log(campo);
			console.log(values[TIPOCAMPO]);
			if(values[TIPOCAMPO] == MODEL){
				var objBuscaDados = {"classname":values[CLASSNAMECAMPO],"action":KEYBUSCA,"usecase":"crud",data:{}}
				var objResposta = getResponse(objBuscaDados);
				console.log(objResposta);
				metodoGeral = $("#"+campo).data('tipo')+"Constroi";
				console.log(metodoGeral);
				var objConfig = {"label":$("#"+campo).data('label'),"value":$("#"+campo).data('value')}
				$("#"+campo).append(window[metodoGeral](objResposta[DATAGERAL][DATAGERAL],objConfig));
			}else{
				
				
			}
		});
	console.log(nomeForm);
    submitGeral(nomeForm,resetForm);
}

function checkboxConstroi(dados){
  var html = '';
  $.each(dados,function(cont,obj){
		selectedOption = obj.selected;
		html += '<label>'+obj.label+'</label><input value="'+obj[objConfig.value]+'" type="'+$("#"+campo).data('tipo')+'" name="'+campo+'" class="'+campo+'"  />';
	});
  return html;
}

function selectConstroi(dados,objConfig){
  var html = '';
	$.each(dados,function(cont,obj){
		selectedOption = obj.selected;
		html += '<option value="'+obj[objConfig.value]+'" >'+obj[objConfig.label]+'</option>';
	});
  return html;
}

// Metodo para validar tipos de ação do sistema

function validaAction(actionStart,objAction){
	var nameAction = "valida"+actionStart;
	window[nameAction](objAction);
}

function validaLogin(objAction){
	submitGeral(objAction.nomeForm,objAction.resetForm);
}

function validaInsert(objAction){
	$("#btnSubmit").val('Cadastrar');
	data = getResponse(objAction[KEYDADOS]);
	if(objAction[KEYCONFIG].attrRemove != ""){
		console.log(data[DATAGERAL]);
		delete data[DATAGERAL][KEYFORM][objAction[KEYCONFIG].attrRemove];
	}
    construirForm(data,objAction[KEYCONFIG].nomeForm,objAction[KEYCONFIG].formReset);

}

function validaExcluiList(objAction){
	var objLinha = objAction.btnClick.parent().parent();
	var table = objLinha.parent().parent();
	var tableHead = table.children('thead');
	var headsArray = new Array();
	tableHead.children().children('th').each(function(i,obj){
		//console.log(i);
		headsArray.push($(this).text());
	});
	var htmlDados = "<h4 class='text-warning'>Deseja realmente excluir este item?</h4>";
	objLinha.children('td').each(function(i,obj){
		
		if(headsArray[i] != "#"){
			htmlDados += "<h4><strong>"+headsArray[i]+": </strong> "+$(this).text()+"</h4>";
		}
	});
	OBJGERAL = objAction.parametros;
	$("#btnConfirmModal").attr("onclick","validaRemover()");
	$("#"+CORPOMODALCONFIRM).html(htmlDados);
	$("#"+MODALCONFIRM).modal('show');
}

function validaRemover(){
	$("#"+MODALCONFIRM).modal('hide');
	OBJGERAL["data"] = {"id":sessionStorage.id}
	var data = getResponse(OBJGERAL);
	validaRetorno(data);
}

function navCentral(url){
  $("#conteudoCentral").load(url);
}

function setDadosForm(objDados){
	console.log(objDados);
	$.each(objDados[0],function(campo,values){
		if($.isPlainObject(values)){
			$("#"+campo).val(values[$("#"+campo).data('value')]);
		}else{
			$("#"+campo).val(values);
		}
	});
}

function validaUpdate(objAction){
	$("#"+DIVHIDDENS).append('<input type="hidden" name="campo.id" id="id" value="'+sessionStorage.id+'" />');
	$("#action").val(SALVARACTION);
	$("#btnSubmit").val('Salvar');
  $("#divSubmit").prepend('<input onClick=navCentral("'+objAction[KEYCONFIG].returnPage+'") class="btn btn-success" type="button" id="btnSubmit"  value="Retornar Consulta" />');
	data = getResponse(objAction[KEYDADOS]);
	construirForm(data,objAction[KEYCONFIG].nomeForm,objAction[KEYCONFIG].formReset);
	setDadosForm(data[DATAGERAL][DATAGERAL])
}

function validaLogout(){
	var objAction = {"action":"logout","usecase":"Login","classname":"Usuario"};
	var data = getResponse(objAction);
	validaRetorno(data);
}

function validaGetSerialForm(idForm){
  var dadosSeriais = "";
  $(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
      e.preventDefault();
      dadosSeriais = serializaVetor($(this).serialize(),2);
  });
  return dadosSeriais;
}

function validaConsulta(objAction){
	var data;

	//return false;
	data = getResponse(objAction[KEYDADOS]);
  //$("#loadContent").load(PATH_API,objAction,function(data){
        console.log(data);
        //data = JSON.parse(data);
        //console.log(data[KEYBUSCA][KEYDADOS]);
        construirTabela(data[DATAGERAL][DATAGERAL],objAction[KEYDADOS].className,objAction[KEYCONFIG]);
        ativaBtnList();
        submitConsulta(objAction.nomeForm,objAction[KEYCONFIG]);
  //});

}

function ativaBtnList(){
	$(document).off('click','.btnActionList');
	$(document).on('click','.btnActionList',function(e){
		var nameAction = $(this).data('tipoaction');
		var objAction = {"url":$(this).data('url'),"btnClick":$(this),"parametros":{"classname":$(this).data('classname'),"action":$(this).data('action'),"usecase":$(this).data('usecase')}};		
		sessionStorage.id = $(this).val();
		validaAction(nameAction,objAction);
	});
}



function construirTabela(dados,nomeTabela,config){
  var htmlTd;
  var htmlTr = "";
  var idItem = "";
  var dataRows;
  var dataHead = config.tableHead;
  if(!$.isEmptyObject(dados)){
	  
	  $.each(dados,function(i, obj){
		//  console.log(i);
		 // console.log(obj);
	      htmlTd = "";
	      idItem = obj[config.labelId];
	      console.log("config "+config);
	      //return false;
	      $.each(dataHead,function(key,value){
	    	  console.log("valor "+value.data);
	    	  console.log("key "+key);
	
	    	  if($.isPlainObject(obj[value.data])){
	    		  console.log(value);
	    		  htmlTd += "<td data-key='"+key+"'>"+obj[value.data][value.label]+"</td>";
	    	  }else{
	    		  htmlTd += "<td data-key='"+key+"'>"+obj[value.data]+"</td>";
	    	  }
	
	      });
	      htmlTd += '<td><button class="btn btn-sm btn-primary btnActionList" data-tipoaction="EditList" type="button" data-url="'+config.urlEdit+'" value="'+idItem+'">Editar</button> <button value="'+idItem+'" data-usecase="Crud" data-action="remover" data-classname="'+config.className+'" class="btn btn-sm btn-danger btnActionList" data-tipoaction="ExcluiList" type="button">Excluir</button></td>';
	      htmlTr += '<tr>'+htmlTd+'</tr>'
	  });
  }else{
	  console.log(config.campoBusca);
	  htmlTr = "<tr><td colspan='"+(dataHead.length + 1)+"'>Não há resultados para '"+$("#"+config.campoBusca).val()+"' </td></tr>";
  }
  //console.log(htmlTr);
  $("#"+config.nomeTabela).children('tbody').html(htmlTr);

}

function validaEditList(objAction){
	navCentral(objAction.url);
}



function getResponse(objAction){
	var resposta;
	var jsonSend = [objAction];
	console.log(jsonSend);
	var stringJson = JSON.stringify(jsonSend);
	console.log(stringJson);
	$.ajax({
		async:false,
		data:{"request":stringJson},
		url:PATH_API,
		type:"POST",
		success: function(objResposta){
				resposta = objResposta;

		},
		error: function(data){
			alert("Houve algum problema!")
			console.log('Erro');
			console.log(data);
		}
	 });
	 return resposta;
	}
/*
    $("#loadContent").load(PATH_API,objAction,function(data){
          //console.log(data);
          resposta = data;

    });

}*/

function ativaBtnModalMsg(config){
	$(document).off('click','#'+BTNMODALMSG);
	$(document).on('click','#'+BTNMODALMSG,function(){
		if(config.dismiss){
			$("#"+MODALMSG).modal('hide');
		}
		if(config.redirect){
			document.location.replace(config.url);
		}
	});
}

function validaStatusLogin(objResposta){
	if(objResposta.loggedin){
		if(!$.isEmptyObject(objResposta.redirect)){
			console.log(MAPNAV[objResposta.redirect.redirectUseCase]);
			if(MAPNAV[objResposta.redirect.redirectUseCase].central){
				navCentral(MAPNAV[objResposta.redirect.redirectUseCase].url);
			}else{
				document.location.replace(MAPNAV[objResposta.redirect.redirectUseCase].url);
			}
		}
	}
}

function validaEnviaDados(objAction,divConfig){
	var vetDadosSerializados = serializaVetor(objAction,"");
	var objConfig = new Object();
	var i = 0;
	$("#"+divConfig).children('input').each(function(data,value){
		console.log(i);
		i++;
		objConfig[$(this).attr('name')] = $(this).val();
	});
    if(typeof sessionStorage.id != "undefined" && sessionStorage.id != ""){
  	  vetDadosSerializados[0].id = sessionStorage.id;
    }
	objConfig[DATAGERAL] = vetDadosSerializados[0];
	var data = getResponse(objConfig);
	return data;
}

function submitConsulta(idForm,config){
	CONFIGTABLE = config;
	$(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
     e.preventDefault();
     var data = validaEnviaDados($(this).serializeArray(),$(this).attr('id')+"divConf");
	 console.log(data);
     //construirTabela(data[KEYBUSCA],CONFIGTABLE.nomeTabela,CONFIGTABLE);
     construirTabela(data[DATAGERAL][DATAGERAL],CONFIGTABLE.nomeTabela,CONFIGTABLE);

  });
}

function submitGeral(idForm,cleanForm){
	$(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
      e.preventDefault();
     
      var data = validaEnviaDados($(this).serializeArray(),$(this).attr('id')+"divConf");
			console.log("Retorno------------");
			
			//data = JSON.parse(data);
			validaRetorno(data)
			if(cleanForm == true){ 
				$(this).each(function(){
					this.reset();
				})
			}
  });
}

                                          

function validaMenu(data){
    var htmlMenu = '';
    $.each(data,function(i,objMenu){
       htmlMenu += '<li class="'+objMenu.ativo+' liMenu"><a href="#" class="btnMenuAction" data-url="'+objMenu.url+'">'+objMenu.name+'</a></li>';
    });
    $(".navMenu").prepend(htmlMenu);
    $(".btnMenuAction").click(function(e){
      sessionStorage.id = "";
      $(".liMenu").removeClass('active');
      $("#conteudoCentral").html('<h4 class="text-warning">Aguarde</h4>');
      console.log($(this).data('url'));
      $("#conteudoCentral").load($(this).data('url'));
      $(this).parent().addClass('active');
    });
}


function validaRetorno(data){
    $(".infoErro").remove();
    console.log(data);
    var objOp = {"dados":"","redirect":""};
		$.each(data,function(key,objs){
				if($.isArray(data[key]) || $.isPlainObject(data[key])){
						if(!$.isEmptyObject(data[key])){
							objOp.dados = data[key];
							objOp.redirect = data["redirect"];
							console.log(key.capitalizeFirstLetter());
							 validaAction(key.capitalizeFirstLetter(),objOp);
						}
				}
		});
}

function validaMessages(objAction){
		var htmlMsg = "";
		$.each(objAction[KEYDADOS],function(i,msg){
			htmlMsg += "<h3 class='text-success'>"+msg+"</h3>";
		});
		$("#"+CORPOMODALMSG).html(htmlMsg);
		$("#"+MODALMSG).modal('show');
		console.log();
		var url = "index.html";
		console.log(url);
		var config = {"redirect":true,"url":url};
		ativaBtnModalMsg(config);
}

function validaUser(objAction){
	console.log(objAction);
}

function validaData(objAction){
	var objData = objAction.dados;
	console.log(objData);
	console.log(objAction.dataType);
}



function validaErroMsg(objAction){
	var htmlMsg = "";
	$.each(objAction,function(i,msg){
		htmlMsg += "<spam class='text-warning'>"+msg+"</spam>";
	});
	
	return htmlMsg;
	
}

function validaRedirect(objAction){
	console.log(objAction);
	if(!objAction.redirect){
		document.location.replace(MAPNAV[objAction.redirect.redirectUseCase].url);
	}
}

function validaErrors(objAction){
		var htmlMsg = "";
		$.each(objAction,function(i,msg){
			htmlMsg += "<h3 class='text-warning'>"+msg+"</h3>";
		});
		$("#"+CORPOMODALMSG).html(htmlMsg);
		$("#"+MODALMSG).modal('show');
    $("#"+MODALTITULO).text(KEYTITULOMODAL);
}

function validaAtberrors(objAction){
	console.log(objAction);
	$.each(objAction[KEYDADOS],function(cont,obj){
		console.log(obj);
		
			$("#"+obj.attributename).parent().append('<div class="alert alert-warning infoErro">'+obj.error+'</div>');
	});
}

$("#"+MODALMSG).on('hidden.bs.modal',function(){
	$(document).find('input').focus();
});
$("#"+MODALMSG).on('show.bs.modal',function(){
	$(document).find('input').focus();
});
