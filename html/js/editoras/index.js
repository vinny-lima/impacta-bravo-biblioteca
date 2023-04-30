getAllEditoras();


function getAllEditoras () {
    
    const queryGetAll = [
        {name: 'page', value: 0},
        {name: 'linesPerPage', value: 10},
        {name: 'direction', value: 'DESC'},
        {name: 'orderBy', value: 'id'}
    ];
    const mapOrder = [
        'id',
        'nomeFantasia',
        'documento',
        'telefone',
        'email'   
    ];
    
    window['dtEditora'] = $('#tb_editoras').DataTable({
        initComplete: function () {
            iniciarComportamentos();
        },
        destroy       : true,
        processing    : true,
        serverSide    : true,
        scrollX       : true,
        autoWidth     : true,
        scrollY       : '55vh',
        scrollCollapse: true,
        responsive    : true,
        paging        : true,
        ordering      : true,
        searching     : false,
        columns: [
            {data: 'id'},
            {data: 'nomeFantasia'},
            {data: 'documento'},
            {data: 'telefone'},
            {data: 'email'},
        ],
        columnDefs: [
            {
                targets: [0],
                type: 'num'
            }
        ],
        ajaxSource: rotas.editoras,
        serverData: function (sSource, aoData, fnCallback) {
            console.log(aoData)

            if (typeof window['dtEditora'] !== 'undefined') {
                queryGetAll[0].value = window['dtEditora'].page();
                queryGetAll[1].value = window['dtEditora'].page.len();
                
                const ordenacaoAtual = window['dtEditora'].order();
                switch (ordenacaoAtual[0][0]) {
                    case 0:
                        queryGetAll[3].value = mapOrder[0];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                    case 1:
                        queryGetAll[3].value = mapOrder[1];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                    case 2:
                        queryGetAll[3].value = mapOrder[2];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                }
            }

            queryGetAll.map(element => aoData.push(element));
            
            $.ajax({
                type: 'GET',
                url : sSource,
                data: aoData,
                success: function (sSource, aoData) {
                    console.log(sSource);
                    // Transforma os IDs em ancoras
                    sSource.content.map(element => {
                        element.id    = `<a class="text-decoration-none" name="atualizar_cadastro" 
                        href="${element.id}"><i class="fa-regular fa-hand-point-right">
                        </i> ${element.id}</a>`;
                    });

                    // Ajusta as propriedades para renderizar a tabela
                    sSource.iTotalDisplayRecords = sSource.totalElements;
                    sSource.iTotalRecords        = sSource.totalElements;
                    sSource.aaData               = sSource.content;
                    fnCallback(sSource, aoData);
                },
                error: function (response, status) {
                    console.log(response);
                }
            });
        }
    });
}


function getEditora(id) {
    $.ajax({
        type   : 'GET',
        url    : `${rotas.editoras}/${id}`,
        success: function (response, status) {
            console.log(response);

            $('#editora_razaoSocial').val(response.razaoSocial);
            $('#editora_nomeFantasia').val(response.nomeFantasia);
            $('#editora_documento').val(response.documento);
            $('#editora_telefone').val(response.telefone);
            $('#editora_email').val(response.email);
            $('#editora_logradouro').val(response.logradouro);
            $('#editora_numero').val(response.numero);
            $('#editora_complemento').val(response.complemento);
            $('#editora_bairro').val(response.bairro);
            $('#editora_municipio').val(response.municipio);
            $('#editora_uf').val(response.uf);
            $('#editora_cep').val(response.cep);

            $('#template_editoras #limpar, #salvar').data('id', response.id);
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function iniciarComportamentos() {
    /* Eventos */
    // Template - Adicionar
    $('#listagem_editoras').on('click', '#adicionar', 
    function () {
        gerenciarEditora();
        $('#editora_razaoSocial').focus();
        return false;
    });
    // Template - Atualizar
    $('#tb_editoras tbody').on('click', 'td a[name="atualizar_cadastro"]', 
    function () {
        getEditora($(this).attr('href'));
        gerenciarEditora('atualizar');
        return false;
    });
    // Salvar
    $('#tb_editoras').on('click', '#salvar', 
    function () {
        const email = document.getElementById("editora_email").name;
        if (!validarEmail(email)) {
            alert("Por favor, insira um endereço de email válido.");
            return false; // impede o envio do formulário
        }
        $(this).submit();
        return false;
    });
    // Voltar para a lista
    $('#template_editoras').on('click', '#voltar', function () {
        $('#listagem_editoras, #template_editoras').toggleClass('d-none');
        resetarFormulario(document.getElementById('form_editora'));
        return false;
    });
   
    // Comportamento do botão limpar/excluir
    $('#form_editora').on('click', '#limpar', 
        function () {
            switch ($(this).data('modo')) {
                case 'limpar':
                    resetarFormulario(this.form);
                    break;
                case 'excluir':
                    deleteEditora()
                    break;
                default:
                    alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
            }
            return false;
    });
    // Pesquisa de CEP
    $('#editora_cep').on('change', function () {
        const cep = $(this).cleanVal();
        if (cep.length === 8) {
            buscarEndereco(cep);
        }
    });

    $('#editora_telefone').mask(MaskTelefone, MaskTelefoneOptions);
    $('#editora_cep').mask('00000-000');
    $('#editora_documento').mask('00.000.000/0000-00', {reverse: true});
}

function buscarEndereco(cep) {
    $.ajax({
        url: `https://viacep.com.br/ws/${cep}/json/`,
        type: 'GET',
        dataType: 'json',
        success: function(data) {
            $('#editora_logradouro').val(data.logradouro);
            $('#editora_bairro').val(data.bairro);
            $('#editora_municipio').val(data.localidade);
            $('#editora_uf').val(data.uf);
            console.log('cep ok');
        },
            error: function(error) {
            console.error(error);
        }
    });
}

function gerenciarEditora (acao = 'adicionar') {
    
    $('#listagem_editoras, #template_editoras').toggleClass('d-none');
    
    switch (acao) {
        case 'adicionar':
                addEditora();
            break;
        case 'atualizar':
                updateEditora();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
    }
}

function addEditora() {
    // Exibição
    $('#template_editoras > header > label').text('Adicionar Editora');
    $('#template_editoras #salvar').text('Salvar');
    
    // Gerencia atributos dos botões
    $('#template_editoras #limpar').text('Limpar').data('modo', 'limpar');
    
    if ($('#template_editoras #salvar').data('id')) {
        $('#template_editoras #salvar').removeData('id');
    }
    
    
    if ($('#template_editoras #limpar').data('id')) {
        $('#template_editoras #limpar').removeData('id');
    }

    
    // Inicia e configura validador do formulário
    $('#template_editoras #form_editora').validate({
        debug: false,
        rules: {
            editora_razaoSocial: {
                required: true
            },
            editora_nomeFantasia: {
                required: true
            },
            editora_documento: {
                required: true 
            },
            editora_telefone: {
                required: true
            },
            editora_email: {
                required: true
            },
            editora_logradouro: {
                required: true
            },
            editora_numero: {
                required: true,
            },
            editora_complemento: {
                required: false
            },
            editora_bairro: {
                required: true
            },
            editora_municipio: {
                required: true
            },
            editora_uf: {
                required: true
            },
            editora_cep: {
                required: true
            }
        },
        messages: {
            editora_razaoSocial : msg_erro_geral,
            editora_nomeFantasia: msg_erro_geral,
            editora_documento   : msg_erro_geral,
            editora_telefone    : msg_erro_geral,
            editora_email       : msg_erro_geral,
            editora_logradouro  : msg_erro_geral,
            editora_numero      : msg_erro_geral,
            editora_bairro      : msg_erro_geral,
            editora_municipio   : msg_erro_geral,
            editora_uf          : msg_erro_geral,
            editora_cep         : msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_editoras form label.error').css('color', 'red');
        },
      
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                razaoSocial : $('#editora_razaoSocial').val(),
                nomeFantasia: $('#editora_nomeFantasia').val(),
                documento   : $('#editora_documento').val(),
                telefone    : $('#editora_telefone').val(),
                email       : $('#editora_email').val(),
                logradouro  : $('#editora_logradouro').val(),
                numero      : $('#editora_numero').val(),
                complemento : $('#editora_complemento').val(),
                bairro      : $('#editora_bairro').val(),
                municipio   : $('#editora_municipio').val(),
                uf          : $('#editora_uf').val(),
                cep         : $('#editora_cep').val()
            }
            console.log(dataVar);
            $.ajax({
                type       : 'POST',
                cache      : false,
                url        : rotas.editoras,
                dataType   : 'json',
                contentType: 'application/json; charset=UTF-8',
                data       : JSON.stringify(dataVar),
                beforeSend : function () {
                    console.log('Enviando requisição');
                },
                complete: function () {
                    console.log('Envio concluído');
                },
                success: function (response, status) {
                    console.log(response);
                    
                    window['dtEditora'].ajax.reload();
                    $('#form_editora footer #voltar').click();
                    resetarFormulario(document.getElementById('form_editora'));
                },
                error: function (response, status) {
                    console.log(response);
                    console.log(status);
                    console.log(response?.responseJSON?.error);
                    console.log(response?.responseJSON?.message);
                    console.log(response?.responseJSON?.status);
                }
            });

            return false;
        }
    });
}

function deleteEditora() {
    $.ajax({
        type   : 'DELETE',
        url    : `${rotas.editoras}/${$('#template_editoras #limpar').data('id')}`,
        success: function (response, status) {
            window['dtEditora'].ajax.reload();
            $('#form_editora footer #voltar').click();
            resetarFormulario(document.getElementById('form_editora'));
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}

function updateEditora() {
    $('#template_editoras > header > label').text('Atualizar Editora');
    $('#template_editoras #salvar').text('Atualizar');
    // Gerencia atributos do botão de limpar/excluir
    $('#template_editoras #limpar').text('Excluir').data('modo', 'excluir');
   

    // Inicia e configura validador do formulário
    $('#template_editoras #form_editora').validate({
        debug: false,
        rules: {
            editora_razaoSocial: {
                required: true
            },
            editora_nomeFantasia: {
                required: true
            },
            editora_documento: {
                required: true 
            },
            editora_telefone: {
                required: true 
            },
            editora_email: {
                required: true
            },
            editora_logradouro: {
                required: true
            },
            editora_numero: {
                required: true,
            },
            editora_complemento: {
                required: false
            },
            editora_bairro: {
                required: true
            },
            editora_municipio: {
                required: true
            },
            editora_uf: {
                required: true
            },
            editora_cep: {
                required: true
            }
        },
        messages: {
            editora_razaoSocial : msg_erro_geral,
            editora_nomeFantasia: msg_erro_geral,
            editora_documento   : msg_erro_geral,
            editora_telefone    : msg_erro_geral,
            editora_email       : msg_erro_geral,
            editora_logradouro  : msg_erro_geral,
            editora_numero      : msg_erro_geral,
            editora_bairro      : msg_erro_geral,
            editora_municipio   : msg_erro_geral,
            editora_uf          : msg_erro_geral,
            editora_cep         : msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) 
            : error.insertAfter(element);
            $('#template_editoras form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                razaoSocial : $('#editora_razaoSocial').val(),
                nomeFantasia: $('#editora_nomeFantasia').val(),
                documento   : $('#editora_documento').val(),
                telefone    : $('#editora_telefone').val(),
                email       : $('#editora_email').val(),
                logradouro  : $('#editora_logradouro').val(),
                numero      : $('#editora_numero').val(),
                complemento : $('#editora_complemento').val(),
                bairro      : $('#editora_bairro').val(),
                municipio   : $('#editora_municipio').val(),
                uf          : $('#editora_uf').val(),
                cep         : $('#editora_cep').val()
            }
            console.log(dataVar);
            $.ajax({
                type       : 'PUT',
                cache      : false,
                url        : `${rotas.editoras}/${$('#template_editoras #salvar').data('id')}`,
                dataType   : 'json',
                contentType: 'application/json; charset=UTF-8',
                data       : JSON.stringify(dataVar),
                beforeSend : function () {
                    console.log('Enviando requisição');
                },
                complete: function () {
                    console.log('Envio concluído');
                },
                success: function (response, status) {
                    console.log(response);
                    
                    window['dtEditora'].ajax.reload();
                    $('#form_editora footer #voltar').click();
                    resetarFormulario(document.getElementById('form_editora'));
                },
                error: function (response, status) {
                    console.log(response);
                    console.log(status);
                    console.log(response?.responseJSON?.error);
                    console.log(response?.responseJSON?.message);
                    console.log(response?.responseJSON?.status);
                }
            });

            return false;
        }
    });
}