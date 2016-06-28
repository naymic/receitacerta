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

//	objAction = {'action':EDITACTION,'className':classe,'id':sessionStorage.id}; <- action, className, useCase, id

// Uso de prototypes

// Prototipo para colocar o primeiro caracter de uma string em Maiusculo
String.prototype.capitalizeFirstLetter = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}



function construirForm(dados,nomeForm,resetForm){ // Construção dinamica de um formulario qualquer
  console.log(dados);
  var data = dados;
	console.log(data);
		var html = '';
		var selectedOption = '';
		var metodoGeral = '';
		$.each(data[KEYDADOS],function(campo,values){
			if($.isArray(values)){
        console.log(campo);
				metodoGeral = $("#"+campo).data('tipo')+"Constroi";
        console.log(metodoGeral);
				$("#"+campo).append(window[metodoGeral](values));
			}else{
				// Caso haja checkbox ou radios estaticas na pagina ela é tratada atraves desse if, 
				//Modo de uso: Coloque o id de um objeto referente aos inputs:checkbox||radio igual ao name deles ex: name="campo.teste" id do objeto -> teste
				//			   Coloque um atributo html5 data-tipo no objeto ex: <div id="teste" data-tipo="checkbox"> ou <div id="teste" data-tipo="radio">
				if($("#"+campo).data('tipo') == 'radio' || $("#"+campo).data('tipo') == 'checkbox'){
					$("input[name='campo."+campo+"']").each(function(i,value){
						if($(this).val() == values){
							$(this).prop('checked',true);
						}
					});
				}else{
					$("#"+campo).val(values);
				}
				
			}
		});
		console.log("form"+nomeForm);
    submitGeral("form"+nomeForm,resetForm);
}

function checkboxConstroi(dados){
  var html = '';
  $.each(dados,function(cont,obj){
		selectedOption = obj.selected;
		html += '<label>'+obj.label+'</label><input value="'+obj.id+'" type="'+$("#"+campo).data('tipo')+'" name="'+campo+'" class="'+campo+'" '+selectedOption+' />';
	});
  return html;
}

function selectConstroi(dados){
  var html = '';
	$.each(dados,function(cont,obj){
		selectedOption = obj.selected;
		html += '<option value="'+obj.id+'"  '+selectedOption+' >'+obj.label+'</option>';
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
	$("#action").val(SALVARACTION);
	$("#btnSubmit").val('Cadastrar');
	data = getResponse(objAction[KEYDADOS]);
 // $("#loadContent").load(PATH_API,objAction,function(data){
        //console.log(data);
        construirForm(data,objAction[KEYDADOS].className,objAction[KEYCONFIG].formReset);
  //});

}

function navCentral(url){
  $("#conteudoCentral").load(url);
}

function validaUpdate(objAction){
	$("#"+DIVHIDDENS).append('<input type="hidden" name="campo.id" id="id" value="'+sessionStorage.id+'" />');
	$("#action").val(SALVARACTION);
	$("#btnSubmit").val('Salvar');
  $("#divSubmit").prepend('<input onClick=navCentral("'+objAction[KEYCONFIG].returnPage+'") class="btn btn-success" type="button" id="btnSubmit"  value="Retornar Consulta" />');
	data = getResponse(objAction[KEYDADOS]);
	construirForm(data,objAction[KEYDADOS].className,objAction[KEYCONFIG].formReset);
}

function validaGetSerialForm(idForm){
  var dadosSeriais = "";
  $(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
      e.preventDefault();
      dadosSeriais = $(this).serialize();
  });
  return dadosSeriais;
}

function validaBusca(objAction){

}

function validaConsulta(objAction){
	var data;

	//return false;
	data = getResponse(objAction[KEYDADOS]);
  //$("#loadContent").load(PATH_API,objAction,function(data){
        console.log(data);
        //data = JSON.parse(data);
        //console.log(data[KEYBUSCA][KEYDADOS]);
        construirTabela(data[KEYBUSCA][KEYDADOS],objAction[KEYDADOS].className,objAction[KEYCONFIG]);
        ativaBtnList();
        submitConsulta("consulta"+objAction[KEYDADOS].className);
  //});

}

function ativaBtnList(){
	$(document).off('click','.btnActionList');
	$(document).on('click','.btnActionList',function(e){
		var nameAction = $(this).data('tipoaction');
		var objAction = {"url":$(this).data('url')};
		sessionStorage.id = $(this).val();
		validaAction(nameAction,objAction);
	});
}



function construirTabela(dados,nomeTabela,config){
  var htmlTd;
  var htmlTr = "";
  var idItem = "";
  $.each(dados,function(i, obj){
	//  console.log(i);
	 // console.log(obj);
      htmlTd = "";
      idItem = obj.id;
      console.log("config "+config);
      //return false;
      $.each(config,function(key,value){
    	  console.log("valor "+value.data);
    	  console.log("key "+key);

    	  if($.isPlainObject(obj[value.data])){
    		  console.log(value);
    		  htmlTd += "<td data-key='"+key+"'>"+obj[value.data][value.label]+"</td>";
    	  }else{
    		  htmlTd += "<td data-key='"+key+"'>"+obj[value.data]+"</td>";
    	  }

      });
      htmlTd += '<td><button class="btn btn-sm btn-primary btnActionList" data-tipoaction="EditList" type="button" data-url="modulos/ingredientes/cad_ingredientes.html" value="'+idItem+'">Editar</button> <button value="'+idItem+'" class="btn btn-sm btn-danger btnActionList" data-tipoaction="ExcluiList" type="button">Excluir</button></td>';
      htmlTr += '<tr>'+htmlTd+'</tr>'
  });
  //console.log(htmlTr);
  $("#"+nomeTabela).children('tbody').append(htmlTr);

}

function validaEditList(objAction){
	navCentral(objAction.url);
}


function getResponse(objAction){
	var resposta;
	console.log(objAction);
	$.ajax({
		async:false,
		data:objAction,
		url:PATH_API,
		type:"POST",
		success: function(objResposta){
			objResposta = JSON.parse(objResposta);
			console.log('Sucesso');
			console.log(objResposta);
			if($.isEmptyObject(objResposta.redirect)){
				console.log("Vazio");
				resposta = objResposta;
			}else{
				validaRetorno(objResposta);
				console.log("Não vazio");
				return false;
			}
			

		},
		error: function(data){
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

function submitConsulta(idForm){
	$(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
      e.preventDefault();
			var data = getResponse($(this).serialize());
			data = JSON.parse(data);
      construirTabela(data[KEYBUSCA][KEYDADOS],$(this).find("#className").val());

  });
}

function submitGeral(idForm,cleanForm){
	$(document).off("submit","#"+idForm);
	$(document).on("submit","#"+idForm,function(e) {
      e.preventDefault();
			var data = getResponse($(this).serialize());
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
    $(".navMenu").html(htmlMenu);
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
							 validaAction(key.capitalizeFirstLetter(),objOp);
						}
				}
		});
}

function validaMsg(objAction){
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

function validaErroMsg(objAction){
	var htmlMsg = "";
	$.each(objAction,function(i,msg){
		htmlMsg += "<spam class='text-warning'>"+msg+"</spam>";
	});
	
	return htmlMsg;
	
}

function validaRedirect(objAction){
	console.log(objAction);
	document.location.replace(MAPNAV[objAction.redirect.redirectUseCase].url);
}

function validaErro(objAction){
		var htmlMsg = "";
		$.each(objAction,function(i,msg){
			htmlMsg += "<h3 class='text-warning'>"+msg+"</h3>";
		});
		$("#"+CORPOMODALMSG).html(htmlMsg);
		$("#"+MODALMSG).modal('show');
    $("#"+MODALTITULO).text(KEYTITULOMODAL);
}

function validaAtb(objAction){
	$.each(objAction,function(cont,obj){
			$("#"+obj.nomeAttributo).parent().append('<div class="alert alert-warning infoErro">'+obj.msg+'</div>');
	});
}

$("#"+MODALMSG).on('hidden.bs.modal',function(){
	$(document).find('input').focus();
});
$("#"+MODALMSG).on('show.bs.modal',function(){
	$(document).find('input').focus();
});
