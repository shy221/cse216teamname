var describe: any;
var it: any;
var expect: any;
var $: any;

describe("Tests of UI: Add Post feature", function() {

    it("UI Test: Add Button Show Add Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(true);
        $('#NewEntryForm-Close').click();        
    });

    it("UI Test: Close Button Hide Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(false);
        $('#NewEntryForm-Close').click();
        expect($("#NewEntryForm").is(':visible')).toEqual(false);
        expect($("#ElementList").is(':visible')).toEqual(true);
    });

});

describe("Tests of UI: Detail Post feature", function() {

    it("UI Test: Detail Button Show Modal", function(){
        // click the button for showing the modal
        $('.ElementList-detailbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(false);     
        $('.ElementList-closebtn').click();
    });

    it("UI Test: Close Button Hide Modal", function(){
        // click the button for showing the modal
        $('.ElementList-detailbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(false);
        $('.ElementList-closebtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(false);
        expect($("#ElementList").is(':visible')).toEqual(true);
    });

});

describe("Tests of UI: Edit Post feature", function() {

    it("UI Test: Edit Button Show Modal", function(){
        // click the button for showing the modal
        $('.ElementList-detailbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(false);
        expect($("#EditEntryForm").is(':visible')).toEqual(false);
        $('.ShowDetail-editbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(false);
        expect($("#ElementList").is(':visible')).toEqual(false);
        expect($("#EditEntryForm").is(':visible')).toEqual(true);     
        $('.EditEntryForm-Cancel').click();
    });

    it("UI Test: Close Button Hide Modal", function(){
        // click the button for showing the modal
        $('.ElementList-detailbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(true);
        expect($("#ElementList").is(':visible')).toEqual(false);
        expect($("#EditEntryForm").is(':visible')).toEqual(false);
        $('.ShowDetail-editbtn').click();
        expect($("#ShowDetail").is(':visible')).toEqual(false);
        expect($("#ElementList").is(':visible')).toEqual(false);
        expect($("#EditEntryForm").is(':visible')).toEqual(true);     
        $('.EditEntryForm-Cancel').click();
        expect($("#ShowDetail").is(':visible')).toEqual(false);
        expect($("#ElementList").is(':visible')).toEqual(true);
        expect($("#EditEntryForm").is(':visible')).toEqual(false);
    });

});