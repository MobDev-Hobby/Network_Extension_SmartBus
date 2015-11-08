var nes = 
{
	createRelayButtonWithImg : function(target_subnet, target_id, channel, container, img_off,img_on)
	{	
		var url = "/do.nes?target_subnet="+target_subnet+"&target_id="+target_id+"&operation=0x0031&arguments="+channel;
		$(container).append(jQuery('<img />').attr('src',img_on).attr("width","150px").attr("height","40px").attr('id', 'rb_on_'+target_subnet+'_'+target_id+'_'+channel).attr('url',url+'640001').click(
			function()
			{
				$.ajax({
		  			url: $('#rb_on_'+target_subnet+'_'+target_id+'_'+channel).attr('url'),
				}).done(function(status) { 
				});
			}
		));
		$(container).append(jQuery('<img />').attr('src',img_off).attr("width","150px").attr("height","40px").attr('id', 'rb_off_'+target_subnet+'_'+target_id+'_'+channel).attr('url',url+'000001').click(
			function()
			{
				$.ajax({
		  			url: $('#rb_off_'+target_subnet+'_'+target_id+'_'+channel).attr('url'),
				}).done(function(status) { 
				});
			}
		));
	},
	
	createDimerSlider : function(target_subnet, target_id, channel, container, img_bg, img_slide)
	{	
		var url = "/do.nes?target_subnet="+target_subnet+"&target_id="+target_id+"&operation=0x0031&arguments="+channel;
		var node = jQuery('<div />').attr('class','slider').css("width","150px").css("height","40px").attr('id', 'sl_'+target_subnet+'_'+target_id+'_'+channel).attr('url',url);
		$(node).slider(
			{
				min: 0,
				max: 100,
				slide:	function(event,slider)
				{
					$.ajax({
		  				url: $('#sl_'+target_subnet+'_'+target_id+'_'+channel).attr('url')+(slider.value.toString(16).length==1?'0'+slider.value.toString(16):slider.value.toString(16))+'0001',
					}).done(function(status) { 
					});
				}
			}
		);
		$(node).find("a").css("width","15px").css("height","15px").css("margin-top","7px").css("background","none").css("border","none").css("background-image","url('"+img_slide+"')").css("background-size","cover");
		$(node).css("background-image","url('"+img_bg+"')").css("background-size","cover").css("height", "41px");
		$(container).append(node);
	},
	
	requestBatchChannelStatus : function(target_subnet, target_id)
	{
		$.ajax({
	  		url: "do.nes?target_id="+target_id+"&target_subnet="+target_subnet+"&operation=0x0033",
		}).done(function(status) { 
		});
	},

	requestChannelStatusForButton : function(target_subnet, target_id, channel)
	{
		$.ajax({
	  		url: "lightstatus.nes?target_id="+target_id+"&target_subnet="+target_subnet+"&channel="+channel,
	  		dataType: "json",
		}).done(function(status) { 
				if(status.intensity < 0)
				{
					$('#rb_on_'+target_subnet+'_'+target_id+'_'+channel).show();
					$('#rb_off_'+target_subnet+'_'+target_id+'_'+channel).show();
				}
				else
				{
					if(status.intensity == 0)
					{
						$('#rb_on_'+target_subnet+'_'+target_id+'_'+channel).show();
						$('#rb_off_'+target_subnet+'_'+target_id+'_'+channel).hide();
					}
					else
					{
						$('#rb_on_'+target_subnet+'_'+target_id+'_'+channel).hide();
						$('#rb_off_'+target_subnet+'_'+target_id+'_'+channel).show();
					}
				}
			});
	},
	
	requestChannelStatusForSlider : function(target_subnet, target_id, channel)
	{
		$.ajax({
	  		url: "lightstatus.nes?target_id="+target_id+"&target_subnet="+target_subnet+"&channel="+channel,
	  		dataType: "json",
		}).done(function(status) { 
				if(status.intensity < 0)
				{
					$('#sl_'+target_subnet+'_'+target_id+'_'+channel).slider("value", 50);
				}
				else
				{
					$('#sl_'+target_subnet+'_'+target_id+'_'+channel).slider("value", status.intensity);
				}
			});
	},
};	