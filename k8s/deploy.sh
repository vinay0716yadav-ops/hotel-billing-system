#!/usr/bin/env bash
# ==============================================================================
# Hotel Billing System - Kubernetes Deployment Script
# ==============================================================================

set -e

NAMESPACE="hotel-system"

echo "=================================================="
echo "🏨 Deploying Hotel Billing System to Kubernetes..."
echo "=================================================="

# 1. Apply Kubernetes manifests
echo "📦 Applying K8s manifests in namespace: $NAMESPACE"
kubectl apply -k .

# 2. Wait for PostgreSQL rollout
echo "⏳ Waiting for PostgreSQL pod to be ready..."
kubectl rollout status deployment/postgres-deployment -n "$NAMESPACE" --timeout=120s

# 3. Wait for Application rollout
echo "⏳ Waiting for Hotel Billing App pods to be ready..."
kubectl rollout status deployment/hotel-billing-app -n "$NAMESPACE" --timeout=180s

echo ""
echo "=================================================="
echo "✅ Deployment Successful!"
echo "=================================================="
echo ""
echo "📊 Kubernetes Resources in '$NAMESPACE':"
kubectl get all -n "$NAMESPACE"

echo ""
echo "🌐 Accessing the Application:"
echo "--------------------------------------------------"
echo "Run this command to forward port 8080 to your local machine:"
echo "👉 kubectl port-forward svc/hotel-billing-service 8080:80 -n $NAMESPACE"
echo ""
echo "Then open in browser: http://localhost:8080"
echo "=================================================="
