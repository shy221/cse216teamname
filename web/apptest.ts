var describe: any;
var it: any;
var expect: any;
var $: any;

function clickEditbyRowIndex(rowIndex) { // rowIndex >=0
    element.all(by.css('table > tbody > tr > td > button.ElementList-detailbtn'))
   .get(rowIndex).click();
}

describe("Tests of UI: Add Post feature", function() {

    it("UI Test: Add Button Show Add Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();        
    });

    it("UI Test: Close Button Hide Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
    });

});

describe("Tests of UI: Edit Post feature", function() {

    it("UI Test: Edit Button Show Modal", function(){
        // click the button for showing the modal
        $('#ElementList-detailbtn').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();        
    });

    it("UI Test: Close Button Hide Modal", function(){
        // click the button for showing the modal
        $('#Navbar-add').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
        $('#NewEntryForm-Close').click();
        expect($("#NewEntryForm").attr("style").indexOf("display: none;")).toEqual(-1);
        expect($("#ElementList").attr("style").indexOf("display: none;")).toEqual(-1);
    });

});