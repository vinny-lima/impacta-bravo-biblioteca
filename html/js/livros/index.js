dtListagemLivros();

function dtListagemLivros () {
    // Parametros default do getAll
    const queryGetAll = [
        {name: 'page', value: 0},
        {name: 'linesPerPage', value: 10},
        {name: 'direction', value: 'ASC'},
        {name: 'orderBy', value: 'titulo'}
    ];
    // Inicializa e configura a tabela
    window['dtLivros'] = $('#tb_livros').DataTable({
        initComplete: function () {
            /* Eventos */
            // Template - Adicionar
            $('#listagem_livros').on('click', '#adicionar', function () {
                gerenciar_template_livro();
                return false;
            });
            // Template - Atualizar
            $('#tb_livros tbbody td').on('click', 'a[name="atualizar_cadastro"]', function () {
                gerenciar_template_livro('atualizar');
                return false;
            });
            // Salvar
            $('#template_livro').on('click', '#salvar', function () {
                $(this).submit();
                return false;
            });
            // Voltar para a lista
            $('#template_livro').on('click', '#voltar', function () {
                $('#listagem_livros, #template_livro').toggleClass('d-none');
                return false;
            });
            /* Configurações gerais */
            // Máscara ISBN
            $('#livro_isbn').mask(MaskISBN, MaskISBNOptions);
            // Validação ISBN
            $.validator.addMethod('validaISBN', function (value, element) {
                // Checa o tamanho do input sem máscara
                if ($(element).cleanVal().length === 10 || $(element).cleanVal().length === 13) {
                    return true;
                }
                return false;
            }, 'ISBN inválido!');
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
            {data: 'titulo'},
            {data: 'subtitulo'},
            {data: 'subtitulo'}, // placeholder
            {data: 'editora.nomeFantasia'},
            {data: 'paginas'} // placeholder
        ],
        columnDefs: [
            {
                targets: [0,5],
                type: 'num'
            }
        ],
        ajaxSource: rotas.livros,
        serverData: function (sSource, aoData, fnCallback) {
            console.log(aoData)

            // console.log(window['dtLivros'].order());

            if (typeof window['dtLivros'] !== 'undefined' && window['dtLivros'].page() !== queryGetAll[0].value) {
                queryGetAll[0].value = window['dtLivros'].page();
            }
            
            if (typeof window['dtLivros'] !== 'undefined' && window['dtLivros'].page.len() !== queryGetAll[1].value) {
                queryGetAll[1].value = window['dtLivros'].page.len();
            }

            queryGetAll.map(element => aoData.push(element));
            
            $.ajax({
                type   : 'GET',
                url    : sSource,
                data   : aoData,
                dataSrc: 'content',
                success: function (sSource, aoData) {
                    console.log(sSource);
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

function gerenciar_template_livro (acao = 'adicionar') {
    // Alterna a exibição
    $('#listagem_livros, #template_livro').toggleClass('d-none');
    // Define o tipo de operação
    switch (acao) {
        case 'adicionar':
                add_livro();
            break;
        case 'atualizar':
                get_livro();
                update_livro();
            break;
        default:
            alert('Erro desconhecido.\nPor favor, recarregue a página e tente novamente.');
            return false;
    }
}

function add_livro() {
    // Exibição
    $('#template_livro > header > label').text('Adicionar Livro');
    
    // Inicia e configura validador do formulário
    $('#template_livro #form_livro').validate({
        debug: false,
        rules: {
            livro_titulo: {
                required: true
            },
            livro_subtitulo: {
                required: true
            },
            livro_autores: {
                required: true
            },
            livro_generos: {
                required: true
            },
            livro_editora: {
                required: true
            },
            livro_paginas: {
                required: true
            },
            livro_isbn: {
                required: true,
                validaISBN: true
            },
            livro_unidades: {
                required: true
            },
            livro_descricao: {
                required: true
            }
        },
        messages: {
            livro_titulo: msg_erro_geral,
            livro_subtitulo: msg_erro_geral,
            livro_autores: msg_erro_geral,
            livro_generos: msg_erro_geral,
            livro_editora: msg_erro_geral,
            livro_paginas: msg_erro_geral,
            livro_isbn: {
                required: msg_erro_geral
            },
            livro_unidades: msg_erro_geral,
            livro_descricao: msg_erro_geral
        },
        errorPlacement: function (error, element) {
            // console.log(error);
            error.insertAfter(element);
            $('#template_livro form label.error').css('color', 'red');
        },
        submitHandler: function (form) {
            console.log('Enviando requisição');
            const dataVar = {
                titulo   : $('#livro_titulo').val(),
                subtitulo: $('#livro_subtitulo').val(),
                descricao: $('#livro_descricao').val(),
                paginas  : $('#livro_paginas').val(),
                isbn     : $('#livro_isbn').val(),
                unidades : $('#livro_unidades').val(),
                editoraId: $('#livro_editora').val(),
                autoresId: $('#livro_autores').val(),
                generosId: $('#livro_generos').val()
            }
            console.log(dataVar);
            $.ajax({
                type       : 'POST',
                cache      : false,
                url        : rotas.livros,
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
                    console.log(`${response}\n${status}\n`);
                },
                error: function (response, status, error) {
                    console.log(`${response}\n${status}\n${error}\n`);
                    console.log(`${response?.responseJSON?.error}\n${response?.responseJSON?.message}\n${response?.responseJSON?.status}\n`);
                }
            });

            return false;
        }
    });
}

function get_livro() {
    
}

function update_livro() {
    $('#template_livro > header > label').text('Atualizar Livro');
    $('#template_livro #salvar').text('Atualizar');
    
}
