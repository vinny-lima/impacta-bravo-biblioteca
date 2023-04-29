/**
 * Conjunto de funções utilitárias para facilitar manipulações avançadas da interface
 */

const MaskISBN = (val) => val.replace(/\D/g, '').length == 13 ? '000-00-00-00000-0' : '00-000-0000-0###';

const MaskISBNOptions = {
    byPassKeys: [32],
    onKeyPress: function (val, e, field, options) {
        field.mask(MaskISBN.apply({}, arguments), options);
    }
}


// Cria validação de ISBN e adiciona ao plugin validator
$.validator.addMethod('validaISBN', function (value, element) {
    // Checa o tamanho do input sem máscara
    if ($(element).cleanVal().length === 10 || $(element).cleanVal().length === 13) {
        return true;
    }
    return false;
}, 'ISBN inválido!');

const MaskTelefone = (val) => val.replace(/\D/g, '').length === 11 ? '(00)00000-00009' : '(00)0000-000099';

const MaskTelefoneOptions = {
    byPassKeys: [32],
    onKeyPress: function (val, e, field, options) {
        field.mask(MaskTelefone.apply({}, arguments), options);
    }
}

const MaskCNPJ = (val) => {
    return val.replace(/\D/g, '').replace(/^(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})$/, '$1.$2.$3/$4-$5')
}
   
const MaskCNPJOptions = {
byPassKeys: [32],
onKeyPress: function (val, e, field, options) {
    field.mask(MaskCNPJ.apply({}, arguments), options);
    }
}

const MaskCep = (val) => {
    return val.replace(/\D/g, '').length === 8 ? '00000-000' : '00000-0000';
  };
  
const MaskCepOptions = {
    onKeyPress: function (val, e, field, options) {
      field.mask(MaskCep.apply({}, arguments), options);
    }
};
  

const resetarFormulario = (form) => {
    $(form).validate().destroy();
    $(form).find('select').val(null).trigger('change');
    form.reset();
}

const pad = (num, size) => {
    let s = num + "";
    while (s.length < size) {
        s = "0" + s;
    }
    return s;
}

const dataUsa2Pt = (data, parte = '') => {
    if (data == null || data == "") {
        return '';
    }

    const data_partes = data.split('-');

    switch (parte) {
        case 'ano':
            return data_partes[0];
        case 'mes':
            return pad(data_partes[1], 2);
        case 'dia':
            return pad(data_partes[2], 2);
        default:
            return pad(data_partes[2], 2) + "/" + pad(data_partes[1], 2) + "/" + data_partes[0];
    }
}

const dataPt2Usa = (data, parte = '') => {
    if (data == null || data == '') {
        return '';
    }

    const data_partes = data.split('/');

    switch (parte) {
        case 'ano':
            return data_partes[2];
        case 'mes':
            return pad(data_partes[1], 2);
        case 'dia':
            return pad(data_partes[0], 2);
        default:
            return data_partes[2] + "-" + pad(data_partes[1], 2) + "-" + pad(data_partes[0], 2);
    }
}

function validarEmail(email) {
    const emailRegex = /^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$/;
    return emailRegex.test(email);
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
  
  
  