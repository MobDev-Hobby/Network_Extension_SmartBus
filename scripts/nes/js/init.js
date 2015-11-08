$(function()
{
	nes.requestBatchChannelStatus("0x01", "0x01");

	nes.createRelayButtonWithImg("0x01", "0x01", "0x01", "#relay_1_1_1", "/img/1_1_1_on.png", "/img/1_1_1_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x02", "#relay_1_1_2", "/img/1_1_2_on.png", "/img/1_1_2_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x03", "#relay_1_1_3", "/img/1_1_3_on.png", "/img/1_1_3_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x04", "#relay_1_1_4", "/img/1_1_4_on.png", "/img/1_1_4_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x05", "#relay_1_1_5", "/img/1_1_5_on.png", "/img/1_1_5_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x06", "#relay_1_1_6", "/img/1_1_6_on.png", "/img/1_1_6_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x07", "#relay_1_1_7", "/img/1_1_7_on.png", "/img/1_1_7_off.png");
	nes.createRelayButtonWithImg("0x01", "0x01", "0x08", "#relay_1_1_8", "/img/1_1_8_on.png", "/img/1_1_8_off.png");
	
	function renewStatuses()
	{	
		nes.requestChannelStatusForButton("0x01","0x01","0x01");
		nes.requestChannelStatusForButton("0x01","0x01","0x02");
		nes.requestChannelStatusForButton("0x01","0x01","0x03");
		nes.requestChannelStatusForButton("0x01","0x01","0x04");
		nes.requestChannelStatusForButton("0x01","0x01","0x05");
		nes.requestChannelStatusForButton("0x01","0x01","0x06");
		nes.requestChannelStatusForButton("0x01","0x01","0x07");
		nes.requestChannelStatusForButton("0x01","0x01","0x08");
		nes.requestChannelStatusForSlider("0x01","0x07","0x01");
		nes.requestChannelStatusForSlider("0x01","0x07","0x02");
		nes.requestChannelStatusForSlider("0x01","0x07","0x03");
		nes.requestChannelStatusForSlider("0x01","0x07","0x04");
		nes.requestChannelStatusForSlider("0x01","0x07","0x05");
		nes.requestChannelStatusForSlider("0x01","0x07","0x06");
		nes.requestChannelStatusForSlider("0x01","0x07","0x07");
		nes.requestChannelStatusForSlider("0x01","0x07","0x08");
	}

	renewStatuses();
	setInterval(renewStatuses,500);

	nes.createDimerSlider("0x01", "0x07", "0x01", "#dimer_1_7_1", "/img/1_7_1_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x02", "#dimer_1_7_2", "/img/1_7_2_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x03", "#dimer_1_7_3", "/img/1_7_3_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x04", "#dimer_1_7_4", "/img/1_7_4_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x05", "#dimer_1_7_5", "/img/1_7_5_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x06", "#dimer_1_7_6", "/img/1_7_6_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x07", "#dimer_1_7_7", "/img/1_7_7_sl.png", "/img/slide.gif");
	nes.createDimerSlider("0x01", "0x07", "0x08", "#dimer_1_7_8", "/img/1_7_8_sl.png", "/img/slide.gif");



});