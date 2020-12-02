function Stack(){
    var items=[];

    this.push=function(element){
        items.push(element);

    }
    this.pop=function(){
        return items.pop();

    }
    this.peek=function(){
        return items.peek();

    }

    this.isEmpty=function(){
        return items.length===0;

    }

    this.size=function(){
        return items.length;

    }

    this.clear=function (){
        items=[];

    }

    this.print=function(){
        return items.toString();
    }
}

function HashMap() {
    this.map = {};
}
HashMap.prototype = {
    put: function (key, value) {// 向Map中增加元素（key, value)
        this.map[key] = value;
    },
    get: function (key) { //获取指定Key的元素值Value，失败返回Null
        if (this.map.hasOwnProperty(key)) {
            return this.map[key];
        }
        return null;
    },
    remove: function (key) { // 删除指定Key的元素，成功返回True，失败返回False
        if (this.map.hasOwnProperty(key)) {
            return delete this.map[key];
        }
        return false;
    },
    removeAll: function () {  //清空HashMap所有元素
        this.map = {};
    },
    keySet: function () { //获取Map中所有KEY的数组（Array）
        var _keys = [];
        for (var i in this.map) {
            _keys.push(i);
        }
        return _keys;
    }
};
HashMap.prototype.constructor = HashMap;



function formatDate(timestamp){
    return new Date(timestamp).toLocaleString();
}

function formatFileSize(len) {
    if(len<1024){
        return len+'B';
    }else if(len<1024*1024){
        return (len/1024.0).toFixed(2)+'KB';
    }else if(len<1024*1024*1024){
        return (len/1024.0/1024.0).toFixed(2)+'MB';
    }else if(len<1024*1024*1024*1024.0){
        return (len/1024.0/1024.0/1024.0).toFixed(2)+'GB';
    }
}



function initIcon() {
    var iconMap=new HashMap();
    iconMap.put('dir',"img/type/dir.png");

    iconMap.put('png',"img/type/png.png");
    iconMap.put('jpg',"img/type/jpg.png");
    iconMap.put('jpeg',"img/type/jpg.png");
    iconMap.put('webp',"img/type/img.png");
    iconMap.put('ttf',"img/type/img.png");

    iconMap.put('mp3',"img/type/mp3.png");
    iconMap.put('flac',"img/type/fla.png");
    iconMap.put('ape',"img/type/aac.png");
    iconMap.put('m4a',"img/type/mp3.png");

    iconMap.put('zip',"img/type/zip.png");
    iconMap.put('rar',"img/type/zip.png");
    iconMap.put('7z',"img/type/zip.png");
    iconMap.put('gz',"img/type/zip.png");

    iconMap.put('pdf',"img/type/pdf.png");
    iconMap.put('doc',"img/type/doc.png");
    iconMap.put('docx',"img/type/doc.png");
    iconMap.put('xls',"img/type/xls.png");
    iconMap.put('xlsx',"img/type/xls.png");
    iconMap.put('ppt',"img/type/ppt.png");
    iconMap.put('pptx',"img/type/xls.png");

    iconMap.put('exe',"img/type/exe.png")
    return iconMap;
}

function fileFormatIcon(type){
    return 'img/type/'+type+'.png';
    // var pic=initIcon().get(type);
    // if(pic==null){
    //     return "img/type/dir.png";
    // }
    // return pic;


    // if(type==='dir'){
    //     return "img/type/dir.png";
    // }else if(type==='png'||type==='jpg'||type==='jpeg'||type==='ttf'){
    //     return "img/type/pic.png";
    // }else if(type==='mp3'||type==='flac'||type==='ape'||type==='m4a'){
    //     return "img/type/music.png";
    // }else if(type==='java'){
    //     return "img/type/java.png";
    // }else if(type==='exe'||type==='appimage'){
    //     return "img/type/exe.png";
    // }else if(type==='zip'||type==='rar'||type==='7z'||type==='gz'){
    //     return "img/type/zip.png";
    // }else {
    //     return "img/type/file.png";
    // }
}
