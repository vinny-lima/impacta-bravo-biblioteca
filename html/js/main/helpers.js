/**
 * Conjunto de funções utilitárias para facilitar manipulações avançadas da interface
 */
const MaskISBN = (val) => {
    return val.replace(/\D/g, '').length == 13 ? '000-00-00-00000-0' : '00-000-0000-0###';
}

const MaskISBNOptions = {
    byPassKeys: [32],
    onKeyPress: function (val, e, field, options) {
        field.mask(MaskISBN.apply({}, arguments), options);
    }
}

const MaskTelefone = (val) => {
    return val.replace(/\D/g, '').length === 11 ? '(00)00000-00009' : '(00)0000-000099';
}

const MaskTelefoneOptions = {
    byPassKeys: [32],
    onKeyPress: function (val, e, field, options) {
        field.mask(MaskTelefone.apply({}, arguments), options);
    }
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
