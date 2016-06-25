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

//	objAction = {'action':EDITACTION,'className':classe,'id':sessionStorage.id}; <- action, className, useCase, id

// Uso de prototypes

String.prototype.capitalizeFirstLetter = function() {
    return this.charAt(0).toUpperCase() + this.slice(1);
}


function construirForm(dados,nomeForm,resetForm){ // Construção dinamica de um formulario
  console.log(dados);
  var data = JSON.parse(dados);
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
				$("#"+campo).val(values);
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
  $("#divSubmit").prepend('<input onClick="navCentral()" class="btn btn-success" type="button" id="btnSubmit"  value="Retornar Consulta" />');
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
        //console.log(data);
        data = JSON.parse(data);
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
	$("#conteudoCentral").load(objAction.url);
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
			console.log('Sucesso');
			console.log(objResposta);
			if(objResposta.session){
					document.location.replace(PATH_LOGIN);
			}else{
				resposta = objResposta;
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
			data = JSON.parse(data);
			var casoUso = data.usecase;
			var actionRetorno = "Retorno"+casoUso;
			validaAction(actionRetorno,data);
			if(cleanForm == true){ 
				$(this).each(function(){
					this.reset();
				})
			}
  });
}

function validaRetornoLogin(data){
	if (textfield.val() != "") {
        $("#output").addClass("alert alert-success animated fadeInUp").html("Welcome back " + "<span style='text-transform:uppercase'>" + textfield.val() + "</span>");
        $("#output").removeClass(' alert-danger');
        $("input").css({
        "height":"0",
        "padding":"0",
        "margin":"0",
        "opacity":"0"
        });
        //change button text 
        $('button[type="submit"]').html("continue")
        .removeClass("btn-info")
        .addClass("btn-default").click(function(){
        $("input").css({
        "height":"auto",
        "padding":"10px",
        "opacity":"1"
        }).val("");
        });
        
        $(".avatar").css({
            "background-image": "url('https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRL1uzmgyrfPwUC7UwnOFHFtkAhQrAUYufbLzWvOt9N8pRt1zlV')"
        });
    } else {
        $("#output").removeClass(' alert alert-success');
        $("#output").addClass("alert alert-danger animated fadeInUp").html("sorry enter a username ");
    }
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


function validaRetornoCrud(data){
    $(".infoErro").remove();
		$.each(data,function(key,objs){
				if($.isArray(data[key])){
						if(!$.isEmptyObject(data[key])){
							 validaAction(key.capitalizeFirstLetter(),data[key]);
						}
				}
		});
}

function validaMsg(objAction){
		var htmlMsg = "";
		$.each(objAction,function(i,msg){
			htmlMsg += "<h3 class='text-success'>"+msg+"</h3>";
		});
		$("#"+CORPOMODALMSG).html(htmlMsg);
		$("#"+MODALMSG).modal('show');
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
