#!/bin/bash

# Set fs.file-max system-wide
echo "fs.file-max = 1100000" | sudo tee -a /etc/sysctl.conf
sudo sysctl -p

# Set user limits (replace 'youruser' with your actual username)
USERNAME="youruser"

sudo bash -c "cat >> /etc/security/limits.conf <<EOF
$USERNAME soft nofile 1100000
$USERNAME hard nofile 1100000
$USERNAME soft nproc 1100000
$USERNAME hard nproc 1100000
EOF"

# Ensure PAM limits is enabled
PAM_FILE="/etc/pam.d/common-session"
if ! grep -q pam_limits.so $PAM_FILE; then
  echo "session required pam_limits.so" | sudo tee -a $PAM_FILE
fi

echo "✅ Limits updated. Please log out and log back in to apply user limits."
echo "✅ Check with: ulimit -n ; ulimit -u"
