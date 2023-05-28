getAllGenerosLiterarios();

function getAllGenerosLiterarios () {
    // Parametros default do getAll
    const queryGetAll = [
        {name: 'page', value: 0},
        {name: 'linesPerPage', value: 10},
        {name: 'direction', value: 'DESC'},
        {name: 'orderBy', value: 'id'}
    ];
    const mapOrder = [
        'id',
        'descricao'
    ];
    // Inicializa e configura a tabela
    window['dtGenerosLiterarios'] = $('#tb_generos').DataTable({
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
            {data: 'descricao'}
        ],
        columnDefs: [
            {
                targets: [0],
                type: 'num'
            }
        ],
        ajaxSource: rotas.generos_literarios,
        serverData: function (sSource, aoData, fnCallback) {
            console.log(aoData)

            if (typeof window['dtGenerosLiterarios'] !== 'undefined') {
                queryGetAll[0].value = window['dtGenerosLiterarios'].page();
                queryGetAll[1].value = window['dtGenerosLiterarios'].page.len();
                
                const ordenacaoAtual = window['dtGenerosLiterarios'].order();
                console.log(ordenacaoAtual);

                switch (ordenacaoAtual[0][0]) {
                    case 0:
                        queryGetAll[3].value = mapOrder[0];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                    case 1:
                        queryGetAll[3].value = mapOrder[1];
                        queryGetAll[2].value = ordenacaoAtual[0][1].toUpperCase();
                        break;
                }
            }

            queryGetAll.map(element => aoData.push(element));
            console.log(queryGetAll);

            $.ajax({
                type: 'GET',
                url : sSource,
                data: aoData,
                success: function (sSource, aoData) {
                    console.log(sSource);
                    // Transforma os IDs em ancoras
                    sSource.content.map(element => {
                        element.id = `<a class="text-decoration-none" name="atualizar_cadastro" href="${element.id}"><i class="fa-regular fa-hand-point-right"></i> ${element.id}</a>`;
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

function getGeneroLiterario(id) {
    $.ajax({
        type   : 'GET',
        url    : `${rotas.generos_literarios}/${id}`,
        success: function (response, status) {
            console.log(response);

            $('#descricao_genero').val(response.descricao);
            $('#template_genero #limpar, #salvar').data('id', response.id);
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}


function iniciarComportamentos() {
    /* Eventos */
    // Template - Adicionar
    $('#listagem_generos').on('click', '#adicionar', function () {
        gerenciarFormGenero();
        $('#descricao_genero').focus();
        return false;
    });
    // Template - Atualizar
    $('#tb_generos tbody').on('click', 'td a[name="atualizar_cadastro"]', function () {
        getGeneroLiterario($(this).attr('href'));
        gerenciarFormGenero('atualizar');
        return false;
    });
    // Salvar
    $('#template_genero').on('click', '#salvar', function () {
        $(this).submit();
        return false;
    });
    // Voltar para a lista
    $('#template_genero').on('click', '#voltar', function () {
        $('#listagem_generos, #template_genero').toggleClass('d-none');
        resetarFormulario(document.getElementById('form_genero'));
        return false;
    });
    // Comportamento do botão limpar/excluir
    $('#form_genero').on('click', '#limpar', function () {
        switch ($(this).data('modo')) {
            case 'limpar':
                resetarFormulario(this.form);
                break;
            case 'excluir':
                deleteGenero()
                break;
            default:
                alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
        }
        return false;
    });
}

function gerenciarFormGenero (acao = 'adicionar') {
    // Alterna a exibição
    $('#listagem_generos, #template_genero').toggleClass('d-none');
    // Define o tipo de operação
    switch (acao) {
        case 'adicionar':
            addGenero();
            break;
        case 'atualizar':
            updateGenero();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
    }
}

function addGenero() {
    // Exibição
    $('#template_genero > header > label').text('Adicionar Gênero Literário');
    
    if ($('#template_genero #salvar').hasClass('d-none')) {
        $('#template_genero #salvar').toggleClass('d-none');
        $('#descricao_genero').prop('readonly', false);
    }
    
    // Gerencia atributos dos botões
    $('#template_genero #limpar').text('Limpar').data('modo', 'limpar');
    
    if ($('#template_genero #salvar').data('id')) {
        $('#template_genero #salvar').removeData('id');
    }
    
    if ($('#template_genero #limpar').data('id')) {
        $('#template_genero #limpar').removeData('id');
    }
    
    // Inicia e configura validador do formulário
    $('#template_genero #form_genero').validate({
        debug: false,
        rules: {
            descricao_genero: {
                required: true
            }
        },
        messages: {
            descricao_genero: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            element.is('select') ? error.appendTo(element.parent()) : error.insertAfter(element);
            $('#template_genero form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                descricao: $('#descricao_genero').val()
            }
            console.log(dataVar);
            // return false; 
            $.ajax({
                type       : 'POST',
                cache      : false,
                url        : rotas.generos_literarios,
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
                    
                    window['dtGenerosLiterarios'].ajax.reload();
                    $('#form_genero footer #voltar').click();
                    resetarFormulario(document.getElementById('form_genero'));
                },
                error: function (response, status) {
                    console.log(response);
                }
            });

            return false;
        }
    });
}

function updateGenero() {
    $('#template_genero > header > label').text('Gênero Literário');
    
    if (!$('#template_genero #salvar').hasClass('d-none')) {
        $('#template_genero #salvar').toggleClass('d-none');
        $('#descricao_genero').prop('readonly', true);
    }
    
    // Gerencia atributos do botão de limpar/excluir
    $('#template_genero #limpar').text('Excluir').data('modo', 'excluir');
}

function deleteGenero() {
    $.ajax({
        type   : 'DELETE',
        url    : `${rotas.generos_literarios}/${$('#template_genero #limpar').data('id')}`,
        success: function (response, status) {
            window['dtGenerosLiterarios'].ajax.reload();
            $('#form_genero footer #voltar').click();
            resetarFormulario(document.getElementById('form_genero'));
        },
        error: function (response, status) {
            console.log(response);
        }
    });
}
