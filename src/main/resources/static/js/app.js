// Hotel Billing System Frontend Logic (Rupees INR Currency)

document.addEventListener('DOMContentLoaded', () => {
    initBillingForm();
    initPaymentModal();
    initBookingForm();
    initRoomForm();
});

let billItemsList = [];

function initBillingForm() {
    const bookingSelect = document.getElementById('billingBookingSelect');
    if (!bookingSelect) return;

    bookingSelect.addEventListener('change', (e) => {
        const selectedOption = e.target.options[e.target.selectedIndex];
        if (!selectedOption || !selectedOption.dataset.rate) return;

        const rate = parseFloat(selectedOption.dataset.rate) || 0;
        const nights = parseInt(selectedOption.dataset.nights) || 1;
        const guestName = selectedOption.dataset.guest || '';
        const roomNumber = selectedOption.dataset.room || '';

        document.getElementById('displayGuest').textContent = guestName;
        document.getElementById('displayRoom').textContent = `Room ${roomNumber}`;
        document.getElementById('displayNights').textContent = `${nights} Night(s)`;
        document.getElementById('displayRate').textContent = `₹${rate.toFixed(2)}/night`;

        const roomTotal = rate * nights;
        document.getElementById('summaryRoomTotal').textContent = `₹${roomTotal.toFixed(2)}`;

        recalculateBillTotals();
    });

    const addItemBtn = document.getElementById('btnAddServiceItem');
    if (addItemBtn) {
        addItemBtn.addEventListener('click', addServiceItemFromSelect);
    }

    const discountInput = document.getElementById('billingDiscount');
    if (discountInput) {
        discountInput.addEventListener('input', recalculateBillTotals);
    }

    const taxInput = document.getElementById('billingTax');
    if (taxInput) {
        taxInput.addEventListener('input', recalculateBillTotals);
    }

    const generateBillForm = document.getElementById('generateBillForm');
    if (generateBillForm) {
        generateBillForm.addEventListener('submit', handleGenerateBillSubmit);
    }
}

function addServiceItemFromSelect() {
    const serviceSelect = document.getElementById('serviceItemSelect');
    const qtyInput = document.getElementById('serviceItemQty');
    if (!serviceSelect || !qtyInput) return;

    const selectedOption = serviceSelect.options[serviceSelect.selectedIndex];
    if (!selectedOption || !selectedOption.value) {
        alert('Please select a service item');
        return;
    }

    const name = selectedOption.dataset.name;
    const price = parseFloat(selectedOption.dataset.price);
    const category = selectedOption.dataset.category;
    const qty = parseInt(qtyInput.value) || 1;

    billItemsList.push({
        itemName: name,
        unitPrice: price,
        quantity: qty,
        category: category,
        totalPrice: price * qty
    });

    renderBillItemsTable();
    recalculateBillTotals();
    qtyInput.value = 1;
}

function removeBillItem(index) {
    billItemsList.splice(index, 1);
    renderBillItemsTable();
    recalculateBillTotals();
}

function renderBillItemsTable() {
    const tbody = document.getElementById('billItemsTableBody');
    if (!tbody) return;

    tbody.innerHTML = '';
    if (billItemsList.length === 0) {
        tbody.innerHTML = '<tr><td colspan="5" class="text-center text-muted py-3">No additional services added.</td></tr>';
        return;
    }

    billItemsList.forEach((item, index) => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><strong>${item.itemName}</strong><br><small class="text-muted">${item.category || 'General'}</small></td>
            <td class="text-center">${item.quantity}</td>
            <td class="text-end">₹${item.unitPrice.toFixed(2)}</td>
            <td class="text-end fw-semibold">₹${(item.unitPrice * item.quantity).toFixed(2)}</td>
            <td class="text-center">
                <button type="button" class="btn btn-sm btn-outline-danger" onclick="removeBillItem(${index})">
                    <i class="fas fa-trash"></i>
                </button>
            </td>
        `;
        tbody.appendChild(tr);
    });
}

function recalculateBillTotals() {
    const bookingSelect = document.getElementById('billingBookingSelect');
    if (!bookingSelect) return;

    const selectedOption = bookingSelect.options[bookingSelect.selectedIndex];
    let roomTotal = 0;
    if (selectedOption && selectedOption.dataset.rate) {
        const rate = parseFloat(selectedOption.dataset.rate) || 0;
        const nights = parseInt(selectedOption.dataset.nights) || 1;
        roomTotal = rate * nights;
    }

    const servicesTotal = billItemsList.reduce((sum, item) => sum + (item.unitPrice * item.quantity), 0);
    const subtotal = roomTotal + servicesTotal;

    const taxRate = parseFloat(document.getElementById('billingTax')?.value || 12.0);
    const discountRate = parseFloat(document.getElementById('billingDiscount')?.value || 0.0);

    const taxAmount = subtotal * (taxRate / 100.0);
    const discountAmount = subtotal * (discountRate / 100.0);
    const netTotal = subtotal + taxAmount - discountAmount;

    if (document.getElementById('summaryServicesTotal')) {
        document.getElementById('summaryServicesTotal').textContent = `₹${servicesTotal.toFixed(2)}`;
    }
    if (document.getElementById('summarySubtotal')) {
        document.getElementById('summarySubtotal').textContent = `₹${subtotal.toFixed(2)}`;
    }
    if (document.getElementById('summaryTaxAmount')) {
        document.getElementById('summaryTaxAmount').textContent = `₹${taxAmount.toFixed(2)} (${taxRate}%)`;
    }
    if (document.getElementById('summaryDiscountAmount')) {
        document.getElementById('summaryDiscountAmount').textContent = `-₹${discountAmount.toFixed(2)} (${discountRate}%)`;
    }
    if (document.getElementById('summaryNetTotal')) {
        document.getElementById('summaryNetTotal').textContent = `₹${netTotal.toFixed(2)}`;
    }
}

async function handleGenerateBillSubmit(e) {
    e.preventDefault();
    const bookingSelect = document.getElementById('billingBookingSelect');
    const bookingId = bookingSelect?.value;
    if (!bookingId) {
        alert('Please select an active booking');
        return;
    }

    const discountPercentage = parseFloat(document.getElementById('billingDiscount')?.value || 0.0);
    const customTaxPercentage = parseFloat(document.getElementById('billingTax')?.value || 12.0);
    const paymentStatus = document.getElementById('billingPaymentStatus')?.value || 'PENDING';
    const paymentMethod = document.getElementById('billingPaymentMethod')?.value || null;
    const paymentTransactionId = document.getElementById('billingTransactionId')?.value || '';
    const notes = document.getElementById('billingNotes')?.value || '';

    const payload = {
        bookingId: parseInt(bookingId),
        items: billItemsList.map(item => ({
            itemName: item.itemName,
            category: item.category,
            quantity: item.quantity,
            unitPrice: item.unitPrice
        })),
        customTaxPercentage: customTaxPercentage,
        discountPercentage: discountPercentage,
        paymentStatus: paymentStatus,
        paymentMethod: paymentMethod,
        paymentTransactionId: paymentTransactionId,
        notes: notes
    };

    try {
        const response = await fetch('/api/v1/bills/generate', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });

        if (!response.ok) {
            const err = await response.json();
            throw new Error(err.message || 'Failed to generate bill');
        }

        const bill = await response.json();
        window.location.href = `/invoice/${bill.id}`;
    } catch (error) {
        alert('Error creating bill: ' + error.message);
    }
}

function initPaymentModal() {
    const payButtons = document.querySelectorAll('.btn-pay-modal');
    payButtons.forEach(btn => {
        btn.addEventListener('click', () => {
            const billId = btn.dataset.billId;
            const invoiceNumber = btn.dataset.invoice;
            const amount = btn.dataset.amount;

            document.getElementById('payModalBillId').value = billId;
            document.getElementById('payModalInvoiceNumber').textContent = invoiceNumber;
            document.getElementById('payModalAmount').textContent = `₹${parseFloat(amount).toFixed(2)}`;
        });
    });

    const paymentForm = document.getElementById('processPaymentForm');
    if (paymentForm) {
        paymentForm.addEventListener('submit', async (e) => {
            e.preventDefault();
            const billId = document.getElementById('payModalBillId').value;
            const method = document.getElementById('payModalMethod').value;
            const txnId = document.getElementById('payModalTxnId').value;
            const notes = document.getElementById('payModalNotes').value;

            try {
                const response = await fetch(`/api/v1/bills/${billId}/pay`, {
                    method: 'POST',
                    headers: { 'Content-Type': 'application/json' },
                    body: JSON.stringify({
                        paymentMethod: method,
                        transactionId: txnId,
                        notes: notes
                    })
                });

                if (!response.ok) throw new Error('Payment processing failed');
                window.location.reload();
            } catch (err) {
                alert(err.message);
            }
        });
    }
}

function initBookingForm() {
    const bookingForm = document.getElementById('newBookingForm');
    if (!bookingForm) return;

    bookingForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const payload = {
            guestFullName: document.getElementById('bookingGuestName').value,
            guestEmail: document.getElementById('bookingGuestEmail').value,
            guestPhone: document.getElementById('bookingGuestPhone').value,
            idProofType: document.getElementById('bookingIdType').value,
            idProofNumber: document.getElementById('bookingIdNumber').value,
            guestAddress: document.getElementById('bookingAddress').value,
            guestCity: document.getElementById('bookingCity').value,
            guestCountry: document.getElementById('bookingCountry').value,
            roomId: parseInt(document.getElementById('bookingRoomId').value),
            checkInDate: document.getElementById('bookingCheckIn').value,
            checkOutDate: document.getElementById('bookingCheckOut').value,
            numberOfGuests: parseInt(document.getElementById('bookingGuests').value) || 1,
            specialRequests: document.getElementById('bookingRequests').value
        };

        try {
            const response = await fetch('/api/v1/bookings', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const err = await response.json();
                throw new Error(err.message || 'Failed to create booking');
            }

            window.location.href = '/bookings';
        } catch (err) {
            alert('Error creating booking: ' + err.message);
        }
    });
}

function initRoomForm() {
    const roomForm = document.getElementById('newRoomForm');
    if (!roomForm) return;

    roomForm.addEventListener('submit', async (e) => {
        e.preventDefault();
        const payload = {
            roomNumber: document.getElementById('roomNumberInput').value,
            roomType: document.getElementById('roomTypeSelect').value,
            pricePerNight: parseFloat(document.getElementById('roomPriceInput').value),
            capacity: parseInt(document.getElementById('roomCapacityInput').value),
            floor: parseInt(document.getElementById('roomFloorInput').value),
            features: document.getElementById('roomFeaturesInput').value
        };

        try {
            const response = await fetch('/api/v1/rooms', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });

            if (!response.ok) {
                const err = await response.json();
                throw new Error(err.message || 'Failed to create room');
            }

            window.location.reload();
        } catch (err) {
            alert('Error adding room: ' + err.message);
        }
    });
}
