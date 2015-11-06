
    function Rest(root){
    	this.ti = {};
    	this.root = root;
    }
    
    Rest.prototype.callback = function(data){
    	var result = JSON.stringify(data)
		$("#restResult").val(result);
    	
    }
    
    Rest.prototype.request = function(){
    	var type = arguments[0];
    	if('GET' == type){
    		this.get(arguments[0],arguments[1],arguments[2]);
    		
    	}else {
    		this.other(arguments[0],arguments[1],arguments[2]);
    	}
    }
    
    Rest.prototype.get = function(){
    	$.ajax({ 
    		type: arguments[0], 
    		url: arguments[1], 
    		contentType: "application/json; charset=utf-8", 
    		dataType: "json", 
    		success: this.callback,
    		error: this.error
    	}); 
    }
    
    Rest.prototype.other = function(){
    	$.ajax({ 
    		type: arguments[0], 
    		url: arguments[1], 
    		data: JSON.stringify(arguments[2]), 
    		contentType: "application/json; charset=utf-8", 
    		dataType: "json", 
    		success: this.callback,
    		error: this.error
    	}); 
    }
    
    Rest.prototype.error = function(err){
    	if(err.status == 421){
    		window.location.href = 'http://localhost:8080/service/login';
    	}else{
    		alert(err.status);
    	}
    }
    
