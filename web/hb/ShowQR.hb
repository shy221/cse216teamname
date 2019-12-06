<div id="ShowQR" class="modal fade" role="dialog">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title">View Your QR Code</h4>
            </div>
            <div class="modal-body">
                <input type = "text" id = "qr-data" onchange = "generateQR()">
                <div id="qrcode"></div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" id="ShowQR-Close">Close</button>
            </div>
        </div>
    </div>
</div>